package com.mmounirou.spotify;

import java.io.File;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.eventbus.EventBus;
import com.google.common.io.Files;
import com.mmounirou.spotify.commands.ArtistCommand;
import com.mmounirou.spotify.commands.Command;
import com.mmounirou.spotify.commands.DropCommand;
import com.mmounirou.spotify.commands.ListCommand;
import com.mmounirou.spotify.commands.RunCommand;
import com.mmounirou.spotify.commands.RunCommand.RunMode;
import com.mmounirou.spotify.dao.DBUtils;
import com.mmounirou.spotify.listener.EventListener.ApplicationEndEvent;
import com.mmounirou.spotify.listener.EventListener.ApplicationStartEvent;
import com.mmounirou.spotify.listener.LoggerListener;
import com.mmounirou.spotify.listener.NewAlbumMailNotifierListener;

@SuppressWarnings("static-access")
public class ReleaseNotifier
{
	private static Logger APP_LOGGER = Logger.getLogger(ReleaseNotifier.class);

	//@formatter:off
	private static final Option LEARN       = OptionBuilder.withLongOpt("learn").withDescription("Matches are not notified but will be skipped in the future").create();
	private static final Option LIST        = OptionBuilder.withLongOpt("list").withDescription("List all artists of the database").create();
	private static final Option RESET       = OptionBuilder.withLongOpt("reset").withDescription("DANGEROUS. Obliterates the database except artists and runs with learn in order to to regain useful state").create();
	private static final Option DROP        = OptionBuilder.withLongOpt("drop").withDescription("DANGEROUS. Obliterates the database artists included").create();
	private static final Option TEST        = OptionBuilder.withLongOpt("test").withDescription(" Verbose what would happen on normal execution").create();			
//	private static final Option CRON        = OptionBuilder.withLongOpt("cron").withDescription("Disables stdout and stderr output, log file used.Reduces logging level slightly.").create();
	private static final Option ARTIST      = OptionBuilder.withLongOpt("artist").withDescription("Insert new artists separated by comma").hasArg().withType(String.class).create();
	private static final Option ARTIST_FILE = OptionBuilder.withLongOpt("file-artist").withDescription("Insert new artists contained in input file.one artist by line").hasArg().withType(File.class).create();
	private static final Option RUN         = OptionBuilder.withLongOpt("run").withDescription("Start normal execution").create();
	private static final Option HELP        = OptionBuilder.withLongOpt("help").withDescription("print usage information").create();

	//@formatter:on
	private static Options OPTIONS = buildOptions();

	private static Options buildOptions()
	{
		//@formatter:off
		return new Options().addOption(DROP)
							.addOption(RESET)
							.addOption(LIST)
							.addOption(ARTIST)
							.addOption(ARTIST_FILE)
							.addOption(TEST)
							.addOption(LEARN)
							.addOption(RUN)
							.addOption(HELP);
		//@formatter:on
	}

	public static void main(String[] args)
	{
		EventBus eventBus = new EventBus(ReleaseNotifier.class.getName());
		initListeners(eventBus);

		try
		{
			eventBus.post(ApplicationStartEvent.of());

			DBUtils.initDataBase();

			CommandLine commandLine = new PosixParser().parse(OPTIONS, args);
			if (commandLine.hasOption(DROP.getLongOpt()))
			{
				Command dropCommand = new DropCommand(eventBus);
				dropCommand.run();
			}
			if (commandLine.hasOption(RESET.getLongOpt()))
			{
				Command command = new DropCommand(eventBus, true);
				command.run();
			}
			if (commandLine.hasOption(ARTIST.getLongOpt()))
			{
				Iterable<String> strArtists = Splitter.on(",").omitEmptyStrings().trimResults().split(commandLine.getOptionValue(ARTIST.getLongOpt()));
				Command artistCommand = new ArtistCommand(eventBus, strArtists);
				artistCommand.run();
			}
			if (commandLine.hasOption(ARTIST_FILE.getLongOpt()))
			{
				File artistFile = (File) commandLine.getParsedOptionValue(ARTIST_FILE.getLongOpt());
				List<String> strArtists = Files.readLines(artistFile, Charsets.UTF_8);
				Command artistCommand = new ArtistCommand(eventBus, strArtists);
				artistCommand.run();
			}
			if (commandLine.hasOption(TEST.getLongOpt()))
			{
				Command command = new RunCommand(eventBus, RunMode.TEST);
				command.run();
			}
			if (commandLine.hasOption(LEARN.getLongOpt()))
			{
				Command command = new RunCommand(eventBus, RunMode.LEARN);
				command.run();
			}
			if (commandLine.hasOption(LIST.getLongOpt()))
			{
				Command command = new ListCommand(eventBus);
				command.run();
			}
			if (commandLine.hasOption(HELP.getLongOpt()))
			{
				usage();
			}
			if (args.length == 0 || commandLine.hasOption(RUN.getLongOpt()))
			{
				Command command = new RunCommand(eventBus, RunMode.NORMAL);
				command.run();
			}

		} catch (ParseException e)
		{
			APP_LOGGER.error(e.getMessage());
			usage();
		} catch (Throwable e)
		{
			APP_LOGGER.error(e.getMessage(), e);
		} finally
		{
			eventBus.post(ApplicationEndEvent.of());
		}
	}

	private static void initListeners(EventBus eventBus)
	{
		eventBus.register(new LoggerListener(APP_LOGGER));
		eventBus.register(new NewAlbumMailNotifierListener(APP_LOGGER));
	}

	private static void usage()
	{
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.setWidth(120);
		helpFormatter.printHelp("$[.bat|.sh]", OPTIONS, true);
	}
}
