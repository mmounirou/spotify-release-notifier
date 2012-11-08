package com.mmounirou.spotify.listener;

import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.AlbumReleaseChecker.NewAlbumEvent;

public class LogAlbumListener
{
	@Subscribe
	public void receiveNewAlbum(NewAlbumEvent newAlbumEvent)
	{
		System.out.println("New Album = " + newAlbumEvent.getAlbum());
	}
}
