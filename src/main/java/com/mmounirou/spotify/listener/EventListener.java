package com.mmounirou.spotify.listener;

import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.commands.ArtistCommand;
import com.mmounirou.spotify.commands.Command;
import com.mmounirou.spotify.commands.DropCommand;
import com.mmounirou.spotify.commands.ListCommand;
import com.mmounirou.spotify.commands.RunCommand;
import com.mmounirou.spotify.commands.RunCommand.RunMode;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public interface EventListener
{
	public static class NewArtistEvent
	{
		private final Artist m_artists;
		private final RunMode m_mode;

		private NewArtistEvent(Artist artists, RunMode mode)
		{
			m_artists = artists;
			m_mode = mode;
		}

		public static NewArtistEvent of(Artist artists, RunMode mode)
		{
			return new NewArtistEvent(artists, mode);
		}

		public Artist getArtist()
		{
			return m_artists;
		}

		public RunMode getMode()
		{
			return m_mode;
		}
	}

	public static class NewAlbumEvent
	{
		private final Album m_albums;
		private final RunMode m_mode;

		private NewAlbumEvent(Album artists, RunMode mode)
		{
			m_albums = artists;
			m_mode = mode;
		}

		public static NewAlbumEvent of(Album albums, RunMode mode)
		{
			return new NewAlbumEvent(albums, mode);
		}

		public Album getAlbum()
		{
			return m_albums;
		}

		public RunMode getMode()
		{
			return m_mode;
		}
	}

	public static class AllAlbumsDroppedEvent
	{
		private AllAlbumsDroppedEvent()
		{
		}

		public static AllAlbumsDroppedEvent of()
		{
			return new AllAlbumsDroppedEvent();
		}
	}

	public static class AllArtistsDroppedEvent
	{
		private AllArtistsDroppedEvent()
		{
		}

		public static AllArtistsDroppedEvent of()
		{
			return new AllArtistsDroppedEvent();
		}
	}

	public class ApplicationEndEvent
	{
		private ApplicationEndEvent()
		{
		}

		public static ApplicationEndEvent of()
		{
			return new ApplicationEndEvent();
		}
	}

	public static class ApplicationStartEvent
	{
		private ApplicationStartEvent()
		{
		}

		public static ApplicationStartEvent of()
		{
			return new ApplicationStartEvent();
		}
	}

	public static class RunCommandStartEvent
	{
		private final RunCommand command;

		private RunCommandStartEvent(RunCommand command)
		{
			this.command = command;
		}

		public RunCommand getCommand()
		{
			return command;
		}

		public static RunCommandStartEvent of(RunCommand command)
		{
			return new RunCommandStartEvent(command);
		}

	}

	public static class RunCommandEndEvent
	{
		private final RunCommand command;

		private RunCommandEndEvent(RunCommand command)
		{
			this.command = command;
		}

		public static RunCommandEndEvent of(RunCommand runCommand)
		{
			return new RunCommandEndEvent(runCommand);
		}

		public RunCommand getCommand()
		{
			return command;
		}
	}

	public static class ArtistCommandEndEvent
	{
		private final ArtistCommand command;

		private ArtistCommandEndEvent(ArtistCommand command)
		{
			this.command = command;
		}

		public static ArtistCommandEndEvent of(ArtistCommand command)
		{
			return new ArtistCommandEndEvent(command);
		}

		public ArtistCommand getCommand()
		{
			return command;
		}
	}

	public static class ArtistCommandStartEvent
	{
		private final ArtistCommand command;

		private ArtistCommandStartEvent(ArtistCommand command)
		{
			this.command = command;
		}

		public static ArtistCommandStartEvent of(ArtistCommand command)
		{
			return new ArtistCommandStartEvent(command);
		}

		public ArtistCommand getCommand()
		{
			return command;
		}
	}

	public static class DropCommandStartEvent
	{
		private final DropCommand command;

		private DropCommandStartEvent(DropCommand command)
		{
			this.command = command;

		}

		public static DropCommandStartEvent of(DropCommand command)
		{
			return new DropCommandStartEvent(command);
		}

		public DropCommand getCommand()
		{
			return command;
		}
	}

	public static class DropCommandEndEvent
	{
		private final DropCommand command;

		private DropCommandEndEvent(DropCommand command)
		{
			this.command = command;
		}

		public static DropCommandEndEvent of(DropCommand command)
		{
			return new DropCommandEndEvent(command);
		}

		public DropCommand getCommand()
		{
			return command;
		}
	}
	
	public static class ListCommandStartEvent
	{
		private final ListCommand command;

		private ListCommandStartEvent(ListCommand command)
		{
			this.command = command;

		}

		public static ListCommandStartEvent of(ListCommand command)
		{
			return new ListCommandStartEvent(command);
		}

		public ListCommand getCommand()
		{
			return command;
		}
	}

	public static class ListCommandEndEvent
	{
		private final ListCommand command;

		private ListCommandEndEvent(ListCommand command)
		{
			this.command = command;
		}

		public static ListCommandEndEvent of(ListCommand command)
		{
			return new ListCommandEndEvent(command);
		}

		public ListCommand getCommand()
		{
			return command;
		}
	}

	public static class DefaultEventListener implements EventListener
	{

		@Subscribe
		public void receiveCommandStart(Command event)
		{
		}

		@Subscribe
		public void receiveCommandEnd(Command event)
		{
		}

		@Subscribe
		public void receiveApplicationStart(ApplicationStartEvent event)
		{
		}

		@Subscribe
		public void receiveApplicationEnd(ApplicationEndEvent event)
		{
		}

		@Subscribe
		public void receiveNewAlbum(NewAlbumEvent event)
		{
		}

		@Subscribe
		public void receiveNewArtist(NewArtistEvent event)
		{
		}

		@Subscribe
		public void receiveAllArtistsDropped(AllArtistsDroppedEvent event)
		{
		}

		@Subscribe
		public void receiveAllAlbumsDropped(AllAlbumsDroppedEvent event)
		{
		}

		@Subscribe
		public void receiveRunCommandStart(RunCommandStartEvent event)
		{
			receiveCommandStart(event.getCommand());
		}

		@Subscribe
		public void receiveRunCommandEnd(RunCommandEndEvent event)
		{
			receiveCommandEnd(event.getCommand());
		}

		@Subscribe
		public void receiveArtistCommandStart(ArtistCommandStartEvent event)
		{
			receiveCommandStart(event.getCommand());
		}

		@Subscribe
		public void receiveArtistCommandEnd(ArtistCommandEndEvent event)
		{
			receiveCommandEnd(event.getCommand());
		}

		@Subscribe
		public void receiveDropCommandStart(DropCommandStartEvent event)
		{
			receiveCommandStart(event.getCommand());
		}

		@Subscribe
		public void receiveDropCommandEnd(DropCommandEndEvent event)
		{
			receiveCommandEnd(event.getCommand());
		}

		@Subscribe
		public void receiveListCommandStart(ListCommandStartEvent event)
		{
			receiveCommandEnd(event.getCommand());
		}

		@Subscribe
		public void receiveListCommandEnd(ListCommandEndEvent event)
		{
			receiveCommandEnd(event.getCommand());
		}

	}

	@Subscribe
	public void receiveApplicationStart(ApplicationStartEvent event);

	@Subscribe
	public void receiveApplicationEnd(ApplicationEndEvent event);

	@Subscribe
	public void receiveNewAlbum(NewAlbumEvent event);

	@Subscribe
	public void receiveNewArtist(NewArtistEvent event);

	@Subscribe
	public void receiveAllArtistsDropped(AllArtistsDroppedEvent event);

	@Subscribe
	public void receiveAllAlbumsDropped(AllAlbumsDroppedEvent event);

	@Subscribe
	public void receiveRunCommandStart(RunCommandStartEvent event);

	@Subscribe
	public void receiveRunCommandEnd(RunCommandEndEvent event);

	@Subscribe
	public void receiveArtistCommandStart(ArtistCommandStartEvent event);

	@Subscribe
	public void receiveArtistCommandEnd(ArtistCommandEndEvent event);

	@Subscribe
	public void receiveDropCommandStart(DropCommandStartEvent event);

	@Subscribe
	public void receiveDropCommandEnd(DropCommandEndEvent event);
	
	@Subscribe
	public void receiveListCommandStart(ListCommandStartEvent event);

	@Subscribe
	public void receiveListCommandEnd(ListCommandEndEvent event);
}
