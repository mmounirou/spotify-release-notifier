package com.mmounirou.spotify.listener;

import org.apache.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.commands.Command;
import com.mmounirou.spotify.listener.EventListener.DefaultEventListener;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public class LoggerListener extends DefaultEventListener
{

	private Logger m_logger;

	public LoggerListener(Logger logger)
	{
		m_logger = logger;
	}

	@Subscribe
	public void receiveApplicationStart(ApplicationStartEvent event)
	{
		m_logger.info("starting ...");
	}

	@Subscribe
	public void receiveApplicationEnd(ApplicationEndEvent event)
	{
		m_logger.info("end");
	}

	@Subscribe
	public void receiveNewAlbum(NewAlbumEvent event)
	{
		Album album = event.getAlbum();
		m_logger.info(String.format("Album : %s : %s : %s : %s", album.getArtist().getName(), album.getName(), album.getHref(), event.getMode()));
	}

	@Subscribe
	public void receiveNewArtist(NewArtistEvent event)
	{
		Artist artist = event.getArtist();
		m_logger.info(String.format("Artist : %s : %s : %s ", artist.getName(), artist.getHref(), event.getMode()));
	}

	@Subscribe
	public void receiveAllArtistsDropped(AllArtistsDroppedEvent event)
	{
		m_logger.info("All Artists Dropped");
	}

	@Subscribe
	public void receiveAllAlbumsDropped(AllAlbumsDroppedEvent event)
	{
		m_logger.info("All Albums Dropped");
	}

	@Subscribe
	public void receiveCommandStart(Command event)
	{
		m_logger.info(String.format("command %s start ....", event.getClass().getName()));
	}

	@Subscribe
	public void receiveCommandEnd(Command event)
	{
		m_logger.info(String.format("command %s end", event.getClass().getName()));
	}

}
