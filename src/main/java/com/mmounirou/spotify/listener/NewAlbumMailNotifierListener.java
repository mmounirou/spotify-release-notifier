package com.mmounirou.spotify.listener;

import java.util.Comparator;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.commands.RunCommand.RunMode;
import com.mmounirou.spotify.dao.AppConfig;
import com.mmounirou.spotify.listener.EventListener.DefaultEventListener;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public class NewAlbumMailNotifierListener extends DefaultEventListener
{
	private static final String FROM = "email.from";
	private static final String TO = "email.to";
	private static final String SUBJECT = "email.subject";
	private static final String SMTP_HOST = "email.smtp_host";
	private static final String SMTP_PORT = "email.smtp_port";
	private static final String SMTP_USERNAME = "email.smtp_username";
	private static final String SMTP_PASSWORD = "email.smtp_password";
	private static final String SMTP_TSL = "email.smtp_tls";
	// private static final String SMTP_SSL = "email.smtp_ssl";

	private static final String DEFAULT_FROM = "test@localhost";
	private static final String DEFAULT_TO = "";
	private static final String DEFAULT_SUBJECT = "Test";
	private static final String DEFAULT_SMTP_HOST = "localhost";
	private static final int DEFAULT_SMTP_PORT = 25;
	private static final String DEFAULT_SMTP_USERNAME = "";
	private static final String DEFAULT_SMTP_PASSWORD = "";
	private static final boolean DEFAULT_SMTP_TSL = false;
	// private static final boolean DEFAULT_SMTP_SSL = false;

	private HashMultimap<Artist, Album> albumsByArtists;
	private RunMode m_runMode;

	
	
	@Subscribe
	public void receiveRunCommandStart(RunCommandStartEvent event)
	{
		albumsByArtists = HashMultimap.create();
		m_runMode = event.getCommand().getRunMode();
	}

	@Subscribe
	public void receiveNewAlbum(NewAlbumEvent event)
	{
		Album album = event.getAlbum();
		assert m_runMode == event.getMode();// The execution is not designed for
											// // execution
		albumsByArtists.put(album.getArtist(), album);
	}

	@Subscribe
	public void receiveRunCommandEnd(RunCommandEndEvent event)
	{
		if (m_runMode == RunMode.NORMAL && !albumsByArtists.isEmpty())
		{
			try
			{
				HtmlEmail email = buildEmailSender();
				email.setHtmlMsg(buildHtmlMessage());
				email.send();
			} catch (Exception e)
			{
				// TODO log
			}

		}
	}

	private String buildHtmlMessage()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>\n");
		Comparator<Artist> comparator = new Comparator<Artist>()
		{
			public int compare(Artist o1, Artist o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		};
		Set<Artist> artistsSorted = Sets.newTreeSet(comparator);
		artistsSorted.addAll(albumsByArtists.keySet());

		for (Artist artist : artistsSorted)
		{
			builder.append(String.format("<b><a href=\"%s\">%s</a></b></br>\n<ul\n", artist.getHref(), artist.getName()));

			for (Album album : albumsByArtists.get(artist))
			{
				builder.append(String.format("    <li><a href=\"%s\">%s</a></li>\n", album.getHref(), album.getName()));
			}

			builder.append("</ul>\n");
		}
		builder.append("</body></html>");
		return builder.toString();
	}

	private HtmlEmail buildEmailSender() throws EmailException, ConfigurationException
	{
		PropertiesConfiguration properties = AppConfig.getAppProperties();
		String from = properties.getString(FROM, DEFAULT_FROM);
		String to = properties.getString(TO, DEFAULT_TO);
		String subject = properties.getString(SUBJECT, DEFAULT_SUBJECT);
		String smtp_host = properties.getString(SMTP_HOST, DEFAULT_SMTP_HOST);
		int smtp_port = properties.getInt(SMTP_PORT, DEFAULT_SMTP_PORT);
		String smtp_username = properties.getString(SMTP_USERNAME, DEFAULT_SMTP_USERNAME);
		String smtp_password = properties.getString(SMTP_PASSWORD, DEFAULT_SMTP_PASSWORD);
		boolean smtp_tsl = properties.getBoolean(SMTP_TSL, DEFAULT_SMTP_TSL);
		// boolean smtp_ssl = properties.getBoolean(SMTP_SSL, DEFAULT_SMTP_SSL);

		HtmlEmail email = new HtmlEmail();
		email.setFrom(from, "Spotify Album Notifier");
		email.addTo(to);
		email.setSubject(subject);
		email.setHostName(smtp_host);
		email.setSmtpPort(smtp_port);
		email.setAuthenticator(new DefaultAuthenticator(smtp_username, smtp_password));
		email.setTLS(smtp_tsl);
		return email;

	}

	public static void main(String[] args) throws ConfigurationException, EmailException
	{

	}
}
