package com.mmounirou.spotify;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.mmounirou.spotify.dao.AlbumDao;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;
import com.mmounirou.spotify.datamodel.Albums;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spoty4j.api.Search;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public class Main
{

	public static class ToDbArtist implements Function<Artist, Artists>
	{
		@Nullable
		public Artists apply(@Nullable Artist input)
		{
			Artists artists = new Artists();
			artists.setName(input.getName());
			artists.setUri(input.getHref());
			return artists;
		}
	}

	public static class ToDbAlbum implements Function<Album, Albums>
	{
		@Nullable
		public Albums apply(@Nullable Album input)
		{
			Albums albums = new Albums();
			albums.setName(input.getName());
			albums.setUri(input.getHref());
			return albums;
		}
	}

	private static void checkAlbumRelease(ArtistDao artistDao, AlbumDao albumDao)
	{
		ImmutableList<Artists> artists = artistDao.all();
		Set<Album> allAlbums = Sets.newHashSet();

		// Get all albums for this
		for ( Artists artist : artists )
		{
			Artist spotifyArtist = new Artist(artist.getUri(), artist.getName()).fetch();
			allAlbums.addAll(spotifyArtist.getAlbums());
		}

		Collection<Album> insertedAlbums = albumDao.addAlbumsIfNotExists(allAlbums);

		for ( Album album : insertedAlbums )
		{
			notifyNewAlbum(album);
		}
	}

	private static void createArtists(ArtistDao artistDao) throws IOException
	{
		Set<Artist> allArtists = Sets.newHashSet();
		for ( String strArtist : CharStreams.readLines(new InputStreamReader(Sample.class.getResourceAsStream("/artists.txt"))) )
		{
			allArtists.addAll(Search.searchArtist(strArtist));
		}
		artistDao.addArtistIfNotExist(FluentIterable.from(allArtists).transform(new ToDbArtist()).toImmutableList());
	}

	private static void notifyNewAlbum(Album album)
	{
		System.out.println(String.format("New Album : %s : %s : %s", album.getHref(), album.getArtist().getName(), album.getName()));
	}

	public static void main(String[] args) throws SQLException, IOException
	{
		Connection connection = DBUtils.initDataBase(false);
		ArtistDao artistDao = new ArtistDao(connection);
		AlbumDao albumDao = new AlbumDao(connection);
		createArtists(artistDao);
		checkAlbumRelease(artistDao, albumDao);
	}
}
