package com.mmounirou.spotify;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.dao.AlbumDao;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public class AlbumReleaseChecker
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

	public static class Events
	{

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

	}

	private EventBus eventBus;

	public AlbumReleaseChecker(EventBus eventBus)
	{
		this.eventBus = eventBus;
	}

	public void checkAlbumRelease(ArtistDao artistDao, AlbumDao albumDao)
	{
		eventBus.post(Events.startScan());

		ImmutableList<Artists> artists = artistDao.all();
		Set<Album> allAlbums = Sets.newHashSet();

		// Get all albums for this
		for (Artists artist : artists)
		{
			try
			{
				System.out.println(String.format("Get albums for %s ...", artist.getName()));
				Artist spotifyArtist = new Artist(artist.getUri(), artist.getName()).fetch();
				List<Album> albums = spotifyArtist.getAlbums();
				allAlbums.addAll(albums);
				System.out.println("{" + Joiner.on(",").join(albums) + "}\n");

			} catch (Throwable e)
			{
				System.err.println("Fail to fetch artist " + e.getMessage());
			}
		}

		Collection<Album> insertedAlbums = albumDao.addAlbumsIfNotExists(allAlbums);

		for (Album album : insertedAlbums)
		{
			eventBus.post(Events.newAlbum(album));
		}

		eventBus.post(Events.endScan());
	}

}
