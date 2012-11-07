package com.mmounirou.spotify.generator;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import com.mysema.query.codegen.BeanSerializer;
import com.mysema.query.sql.codegen.DefaultNamingStrategy;
import com.mysema.query.sql.codegen.MetaDataExporter;
import com.mysema.query.sql.codegen.NamingStrategy;

public final class GeneratorUtils
{

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
