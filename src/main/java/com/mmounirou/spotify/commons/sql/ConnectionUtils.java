package com.mmounirou.spotify.commons.sql;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionUtils
{
	private ConnectionUtils()
	{
	}

	public static void closeQuietly(Connection connection)
	{
		if ( connection != null )
		{
			try
			{
				connection.close();
			}
			catch ( SQLException e )
			{
				// Do nothing
			}
		}
	}
}
