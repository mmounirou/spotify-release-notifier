package com.mmounirou.spotify.commands;

import java.sql.Connection;
import java.sql.SQLException;

import com.mmounirou.spotify.dao.AlbumDao;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;

public class DropCommand implements Command
{

	private boolean m_inResetMode;

	public DropCommand()
	{
		this(false);
	}

	public DropCommand(boolean inResetMode)
	{
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
				}

				new AlbumDao(connection).deleteAll();
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
