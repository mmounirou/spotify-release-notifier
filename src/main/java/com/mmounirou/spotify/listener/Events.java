package com.mmounirou.spotify.listener;

import com.mmounirou.spotify.commands.RunCommand.RunMode;
import com.mmounirou.spotify.datamodel.Albums;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spoty4j.core.Album;

public class Events
{
	public static class NewAlbumEvent
	{

		private final Album album;

		public NewAlbumEvent(Album album)
		{
			this.album = album;
		}

		public Album getAlbum()
		{
			return album;
		}

	}

	public static class EndEvent
	{

	}

	public static class StartEvent
	{

	}

	public static StartEvent startScan()
	{
		return new StartEvent();
	}

	public static EndEvent endScan()
	{
		return new EndEvent();
	}

	public static NewAlbumEvent newAlbum(Album album)
	{
		return new NewAlbumEvent(album);
	}

	public static Object newArtist(Artists addedArtist)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static Object allArtistsDropped()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static Object allAlbumsDropped()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static Object newAlbum(Albums albums, RunMode runMode)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
