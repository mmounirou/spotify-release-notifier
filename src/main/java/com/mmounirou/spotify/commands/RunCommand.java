package com.mmounirou.spotify.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.commons.sql.ConnectionUtils;
import com.mmounirou.spotify.dao.AlbumDao;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;
import com.mmounirou.spotify.datamodel.Albums;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spotify.listener.Events;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public class RunCommand implements Command
{

	private EventBus m_eventBus;
	private RunMode m_runMode;

	public enum RunMode
	{
		TEST,
		LEARN,
		NORMAL;
	}

	public RunCommand(EventBus eventBus)
	{
		this(eventBus, RunMode.NORMAL);
	}

	public RunCommand(EventBus eventBus, RunMode runMode)
	{
		m_eventBus = eventBus;
		m_runMode = runMode;
	}

	public void run() throws CommandException
	{

		Function<Albums, String> toHref = new Function<Albums, String>()
		{

			@Nullable
			public String apply(@Nullable Albums input)
			{
				return input.getUri();
			}
		};

		Connection connection = null;
		try
		{
			connection = DBUtils.connectToDb();
			ArtistDao artistDao = new ArtistDao(connection);
			AlbumDao albumDao = new AlbumDao(connection);

			Map<String, Albums> albumsById = Maps.uniqueIndex(fetchAlbums(artistDao.all()), toHref);
			Set<String> existingAlbumsHref = albumDao.exist(ImmutableList.copyOf(albumsById.keySet())).toImmutableSet();

			// in the map remove all existing albums
			albumsById.keySet().removeAll(existingAlbumsHref);

			Collection<Albums> newAlbums = albumsById.values();

			if ( m_runMode == RunMode.LEARN || m_runMode == RunMode.NORMAL )
			{
				albumDao.addAlbums(newAlbums);
			}

			fireNewAlbum(newAlbums);

		}
		catch ( SQLException e )
		{
			throw new CommandException(e);
		}
		finally
		{
			ConnectionUtils.closeQuietly(connection);
		}

	}

	private void fireNewAlbum(Iterable<Albums> newAlbums)
	{
		for ( Albums albums : newAlbums )
		{
			m_eventBus.post(Events.newAlbum(albums, m_runMode));
		}
	}

	private FluentIterable<Albums> fetchAlbums(Iterable<Artists> artists) throws CommandException
	{
		Function<Artists, Artist> fetchArtist = new Function<Artists, Artist>()
		{

			@Nullable
			public Artist apply(@Nullable Artists artist)
			{
				// TODO wrap around a try catch
				return new Artist(artist.getUri(), artist.getName()).fetch();
			}
		};

		Function<Artist, Iterable<Album>> getAlbums = new Function<Artist, Iterable<Album>>()
		{

			@Nullable
			public Iterable<Album> apply(@Nullable Artist artist)
			{
				// TODO wrap around a try catch
				return artist.getAlbums();
			}
		};

		Function<Album, Albums> toDbAlbum = new Function<Album, Albums>()
		{

			@Nullable
			public Albums apply(@Nullable Album input)
			{
				Albums albums = new Albums();
				albums.setName(input.getName());
				albums.setUri(input.getHref());
				return albums;
			}
		};

		//@formatter:off
		return FluentIterable.from(FluentIterable.from(artists)
										  .transform(fetchArtist)
										  .transformAndConcat(getAlbums)
										  .toImmutableSet())
							  .transform(toDbAlbum);

	}
}
