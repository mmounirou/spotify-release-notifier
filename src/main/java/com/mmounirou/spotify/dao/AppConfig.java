package com.mmounirou.spotify.dao;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

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

}
