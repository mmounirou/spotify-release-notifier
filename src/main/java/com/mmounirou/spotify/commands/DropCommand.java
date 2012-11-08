package com.mmounirou.spotify.commands;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.AlbumReleaseChecker.Events;
import com.mmounirou.spotify.dao.AlbumDao;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;

public class DropCommand implements Command
{

	private boolean m_inResetMode;
	private EventBus m_eventBus;

	public DropCommand(EventBus eventBus)
	{
		this(eventBus,false);
	}

	public DropCommand(EventBus eventBus,boolean inResetMode)
	{
		m_eventBus = eventBus;
		m_inResetMode = inResetMode;
	}

	public void run() throws CommandException
	{
		try
		{
			Connection connection = DBUtils.connectToDb();
			try
			{
				if ( !m_inResetMode )
				{
					new ArtistDao(connection).deleteAll();
					m_eventBus.post(Events.allArtistsDropped());
				}

				new AlbumDao(connection).deleteAll();
				m_eventBus.post(Events.allAlbumsDropped());

			}
			finally
			{
				connection.close();
			}
		}
		catch ( SQLException e )
		{
			throw new CommandException(e);
		}
	}

}
