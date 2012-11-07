package com.mmounirou.spotify.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.io.CharStreams;
import com.mmounirou.spotify.Sample;


public class DBUtils
{

	public static Connection initDataBase(boolean dropDb) throws SQLException, IOException
	{
		// load the sqlite-JDBC driver using the current class loader
		try
		{
			Class.forName("org.sqlite.JDBC");
		}
		catch ( ClassNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Connection connection = null;

		File dbFile = new File("spotify.db");
		if ( dropDb )
		{
			dbFile.delete();
		}

		boolean newDB = !dbFile.exists();

		// create a database connection
		connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath()));

		if ( newDB )
		{
			Statement statement = connection.createStatement();
			try
			{
				statement.setQueryTimeout(30); // set timeout to 30 sec.

				for ( String strQuery : CharStreams.readLines(new InputStreamReader(Sample.class.getResourceAsStream("/database.sql"))) )
				{
					statement.execute(strQuery);
				}

			}
			finally
			{
				statement.close();
			}
		}

		return connection;
	}

}
