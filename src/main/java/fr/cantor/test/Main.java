package fr.cantor.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.mmounirou.spoty4j.api.Search;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;
import com.mmounirou.spoty4j.core.Track;

import fr.cantor.test.dao.AlbumDao;
import fr.cantor.test.dao.ArtistDao;
import fr.cantor.test.dao.DBUtils;
import fr.cantor.test.dao.TrackDao;
import fr.cantor.test.datamodel.Albums;
import fr.cantor.test.datamodel.Artists;
import fr.cantor.test.datamodel.Tracks;

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
			albums.setAlbumartist(input.getArtist().getHref());
			return albums;
		}
	}


	public static void main(String[] args) throws SQLException, IOException
	{
		Connection connection = DBUtils.initDataBase(true);
		ArtistDao artistDao = new ArtistDao(connection);
		AlbumDao albumDao = new AlbumDao(connection);
		TrackDao trackDao = new TrackDao(connection);

		if ( artistDao.getArtistCount() == 0 )
		{
			Set<Artist> allArtists = Sets.newHashSet();
			for ( String strArtist : CharStreams.readLines(new InputStreamReader(Sample.class.getResourceAsStream("/artists.txt"))) )
			{
				allArtists.addAll(Search.searchArtist(strArtist));
			}
			artistDao.addArtists(FluentIterable.from(allArtists).transform(new ToDbArtist()));

		}

		ImmutableList<Artists> artists = artistDao.all();
		for ( Artists artist : artists )
		{
			Artist spotifyArtist = new Artist(artist.getUri(), artist.getName()).fetch();
			for ( Album spotifyAlbum : spotifyArtist.getAlbums() )
			{
				spotifyAlbum = spotifyAlbum.fetch();
				spotifyAlbum.setArtist(spotifyArtist);

				if ( albumDao.exist(spotifyAlbum.getHref()) )
				{
					for ( Track spotifyTrack : spotifyAlbum.getTracks() )
					{
						spotifyTrack = spotifyTrack.fetch();
						spotifyTrack.setAlbum(spotifyAlbum);
						spotifyTrack.setArtist(spotifyArtist);

						if ( !trackDao.exist(spotifyTrack.getHref()) )
						{
							addTrack(trackDao, spotifyTrack, spotifyAlbum, spotifyArtist);
							notifyNewTrack(artist, spotifyAlbum, spotifyTrack);
						}
					}
				}
				else
				{
					addAlbum(albumDao,trackDao, spotifyAlbum, spotifyArtist);
					notifyNewAlbum(artist, spotifyAlbum);
				}

			}
		}
	}

	private static void addTrack(TrackDao trackDao, Track spotifyTrack,Album album,Artist artist)
	{
		trackDao.addTrack(toDbTrack(spotifyTrack, album, artist));
	}

	private static Tracks toDbTrack(Track input,Album album,Artist artist)
	{
		Tracks tracks = new Tracks();
		tracks.setName(input.getName());
		tracks.setUri(input.getHref());
		tracks.setTrackalbum(album.getHref());
		tracks.setTrackartist(artist.getHref());
		return tracks;
	}

	private static void addAlbum(AlbumDao albumDao,TrackDao trackDao, Album spotifyAlbum,Artist artist)
	{
		albumDao.addAlbum(new ToDbAlbum().apply(spotifyAlbum));
		for ( Track track : spotifyAlbum.getTracks() )
		{
			addTrack(trackDao, track, spotifyAlbum, artist);
		}
	}

	private static void notifyNewTrack(Artists artist, Album album, Track track)
	{
		System.out.println(String.format("New Track : %s: %s : %s : %s",track.getHref(), artist.getName(), album.getName(), track.getName()));
	}

	private static void notifyNewAlbum(Artists artist, Album album)
	{
		System.out.println(String.format("New Album : %s : %s : %s",album.getHref(), artist.getName(), album.getName()));
	}
}
