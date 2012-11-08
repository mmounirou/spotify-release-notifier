package com.mmounirou.spotify.dao;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.mmounirou.spotify.commons.util.AutoUpdatedPropertiesConfiguration;

public final class AppConfig
{
	private AppConfig()
	{
	}

	public static File getDbFile()
	{
		File appDirectoryFile = getAppDirectory();
		return new File(appDirectoryFile, "spotify-release.db");
	}

	private static File getAppDirectory()
	{
		File appDirectoryFile = new File(FileSystemView.getFileSystemView().getHomeDirectory(), ".spotify-release");
		appDirectoryFile.mkdirs();
		return appDirectoryFile;
	}

	private static File getAppPropertiesFile()
	{
		return new File(getAppDirectory(),"app.properties");
	}

	
	public static PropertiesConfiguration getAppProperties() throws ConfigurationException
	{
		PropertiesConfiguration configuration = new AutoUpdatedPropertiesConfiguration(getAppPropertiesFile());
		configuration.setAutoSave(true);
		return configuration;
	}


}
