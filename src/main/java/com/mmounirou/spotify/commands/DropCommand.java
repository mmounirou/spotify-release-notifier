package com.mmounirou.spotify.commands;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.commons.sql.ConnectionUtils;
import com.mmounirou.spotify.dao.AlbumDao;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;
import com.mmounirou.spotify.listener.EventListener.AllAlbumsDroppedEvent;
import com.mmounirou.spotify.listener.EventListener.AllArtistsDroppedEvent;
import com.mmounirou.spotify.listener.EventListener.DropCommandEndEvent;
import com.mmounirou.spotify.listener.EventListener.DropCommandStartEvent;

public class DropCommand implements Command
{

	private boolean m_inResetMode;
	private EventBus m_eventBus;

	public DropCommand(EventBus eventBus)
	{
		this(eventBus, false);
	}

	public DropCommand(EventBus eventBus, boolean inResetMode)
	{
		m_eventBus = eventBus;
		m_inResetMode = inResetMode;
	}

	public void run() throws CommandException
	{
		m_eventBus.post(DropCommandStartEvent.of(this));
		Connection connection = null;
		try
		{
			connection = DBUtils.connectToDb();

			if ( !m_inResetMode )
			{
				new ArtistDao(connection).deleteAll();
				m_eventBus.post(AllArtistsDroppedEvent.of());
			}

			new AlbumDao(connection).deleteAll();
			m_eventBus.post(AllAlbumsDroppedEvent.of());
		}
		catch ( SQLException e )
		{
			throw new CommandException(e);
		}
		finally
		{
			ConnectionUtils.closeQuietly(connection);
			m_eventBus.post(DropCommandEndEvent.of(this));
		}

	}

}
