package com.mmounirou.spotify.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.io.CharStreams;
import com.mysema.query.codegen.BeanSerializer;
import com.mysema.query.sql.codegen.DefaultNamingStrategy;
import com.mysema.query.sql.codegen.MetaDataExporter;
import com.mysema.query.sql.codegen.NamingStrategy;

public final class GeneratorUtils
{

	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{

			// create a database connection
			connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", "sample.db"));
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			for ( String strQuery : CharStreams.readLines(new InputStreamReader(GeneratorUtils.class.getResourceAsStream("/database.sql"))) )
			{
				System.out.println(strQuery);
				statement.execute(strQuery);
			}

			// statement.executeBatch();

			// statement.close();

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

	private GeneratorUtils()
	{
	}

	public static void generateTables(Connection conn, final String targetDirectory, final String schemaPattern) throws SQLException
	{

		MetaDataExporter exporter = new MetaDataExporter();

		exporter.setTargetFolder(new File(targetDirectory));
		exporter.setSchemaPattern(schemaPattern);
		exporter.setTableNamePattern("t_%");
		NamingStrategy namingStrategy = new DefaultNamingStrategy()
		{
			@Override
			public String getClassName(String tableName)
			{
				return super.getClassName(tableName).substring(1);
			}

		};
		exporter.setNamingStrategy(namingStrategy);

		String strPackageName = GeneratorUtils.class.getPackage().getName();
		exporter.setPackageName(strPackageName.replaceFirst(".generator$", ".datamodel.query"));
		exporter.setBeanPackageName(strPackageName.replaceFirst(".generator$", ".datamodel"));

		exporter.setBeanSerializer(new BeanSerializer());

		exporter.export(conn.getMetaData());
	}
}
