package com.mmounirou.spotify;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.io.CharStreams;
import com.mmounirou.spotify.generator.GeneratorUtils;


public class Sample
{
	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{

			// create a database connection
			connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s","sample.db"));
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			for ( String strQuery : CharStreams.readLines(new InputStreamReader(Sample.class.getResourceAsStream("/database.sql"))) )
			{
				System.out.println(strQuery);
				statement.execute(strQuery);
			}

			//statement.executeBatch();
			
			//statement.close();
			
			GeneratorUtils.generateTables(connection, "src/main/java", "");

		}
		catch ( SQLException e )
		{
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if ( connection != null )
					connection.close();
			}
			catch ( SQLException e )
			{
				// connection close failed.
				System.err.println(e);
			}
		}
	}
}