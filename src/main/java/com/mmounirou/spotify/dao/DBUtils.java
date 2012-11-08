package com.mmounirou.spotify.dao;

import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.io.CharStreams;
import com.mmounirou.spotify.commons.sql.ConnectionUtils;
import com.mmounirou.spotify.generator.GeneratorUtils;

public class DBUtils
{

	public static void initDataBase() throws Exception
	{
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		File dbFile = AppConfig.getDbFile();
		boolean newDB = !dbFile.exists();
		connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath()));
		try
		{
			if ( newDB )
			{
				Statement statement = connection.createStatement();
				try
				{
					statement.setQueryTimeout(30); // set timeout to 30 sec.

					for ( String strQuery : CharStreams.readLines(new InputStreamReader(GeneratorUtils.class.getResourceAsStream("/database.sql"))) )
					{
						statement.execute(strQuery);
					}

				}
				finally
				{
					statement.close();
				}
			}
		}
		finally
		{
			ConnectionUtils.closeQuietly(connection);
		}
	}

	public static Connection connectToDb() throws SQLException
	{
		return DriverManager.getConnection(String.format("jdbc:sqlite:%s", AppConfig.getDbFile().getAbsolutePath()));
	}

}
