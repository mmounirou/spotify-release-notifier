package com.mmounirou.spotify.listener;

import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.commands.ArtistCommand;
import com.mmounirou.spotify.commands.Command;
import com.mmounirou.spotify.commands.DropCommand;
import com.mmounirou.spotify.commands.ResetCommand;
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

	public abstract static class DefaultEventListener implements EventListener
	{

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
		public void receiveCommandStart(Command event)
		{
		}

		@Subscribe
		public void receiveCommandEnd(Command event)
		{
		}

		@Subscribe
		public void receiveRunCommandStart(RunCommand event)
		{
		}

		@Subscribe
		public void receiveRunCommandEnd(RunCommand event)
		{	
		}

		@Subscribe
		public void receiveArtistCommandStart(ArtistCommand event)
		{
			
		}

		@Subscribe
		public void receiveArtistCommandEnd(ArtistCommand event)
		{
		}

		@Subscribe
		public void receiveDropCommandStart(DropCommand event)
		{
		}

		@Subscribe
		public void receiveDropCommandEnd(DropCommand event)
		{
		}

		@Subscribe
		public void receiveResetCommandStart(ResetCommand event)
		{
		}

		@Subscribe
		public void receiveResetCommandEnd(ResetCommand event)
		{
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
	public void receiveCommandStart(Command event);

	@Subscribe
	public void receiveCommandEnd(Command event);

	@Subscribe
	public void receiveRunCommandStart(RunCommand event);

	@Subscribe
	public void receiveRunCommandEnd(RunCommand event);
	
	@Subscribe
	public void receiveArtistCommandStart(ArtistCommand event);

	@Subscribe
	public void receiveArtistCommandEnd(ArtistCommand event);
	
	
	@Subscribe
	public void receiveDropCommandStart(DropCommand event);

	@Subscribe
	public void receiveDropCommandEnd(DropCommand event);
	
	
	@Subscribe
	public void receiveResetCommandStart(ResetCommand event);

	@Subscribe
	public void receiveResetCommandEnd(ResetCommand event);

}
