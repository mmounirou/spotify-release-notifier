package com.mmounirou.spotify.listener;

import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.listener.Events.NewAlbumEvent;

public class LogAlbumListener
{
	@Subscribe
	public void receiveNewAlbum(NewAlbumEvent newAlbumEvent)
	{
		System.out.println("New Album = " + newAlbumEvent.getAlbum());
	}
}
