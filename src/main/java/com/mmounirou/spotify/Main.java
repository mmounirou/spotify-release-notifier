package com.mmounirou.spotify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
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

	public static class PrefectMatch implements Predicate<Artist>
	{

		private String strArtist;

		public PrefectMatch(String strArtist)
		{
			this.strArtist = strArtist;
		}

		public boolean apply(@Nullable Artist artist)
		{
			return StringUtils.equalsIgnoreCase(strArtist, artist.getName());
		}

	}

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

	private static void createArtists(ArtistDao artistDao) throws IOException
	{
		Set<Artist> allArtists = Sets.newHashSet();
		for (String strArtist : CharStreams.readLines(new InputStreamReader(Sample.class.getResourceAsStream("/artists.txt"))))
		{
			if (StringUtils.isNotBlank(strArtist))
			{
				ImmutableList<Artist> spotifyArtist = FluentIterable.from(Search.searchArtist(strArtist)).filter(new PrefectMatch(strArtist)).toImmutableList();
				allArtists.addAll(spotifyArtist);
				System.out.println("Fetch Artist : " + strArtist + " : " + Joiner.on(",").join(spotifyArtist));
			}
		}
		artistDao.addArtistIfNotExist(FluentIterable.from(allArtists).transform(new ToDbArtist()).toImmutableList());
	}

	public static void main(String[] args) throws SQLException, IOException
	{

		boolean dropDataBase = false;
		EventBus eventBus = new EventBus();

		if(args.length == 1)
		{
			Properties properties = new Properties();
			properties.load(new FileInputStream(args[0]));
			MailAlbumListener mailListener = new MailAlbumListener(properties);
			eventBus.register(mailListener);
		}
		
		Object eventListener = new LogAlbumListener();
		eventBus.register(eventListener);
		
		long begin = System.currentTimeMillis();
		AlbumReleaseChecker scanner = new AlbumReleaseChecker(eventBus);

		Connection connection = DBUtils.initDataBase(dropDataBase);
		ArtistDao artistDao = new ArtistDao(connection);
		AlbumDao albumDao = new AlbumDao(connection);
		createArtists(artistDao);
		
		scanner.checkAlbumRelease(artistDao, albumDao);

		long end = System.currentTimeMillis();

		System.out.println("Export take " + (end - begin) / 1000 + " seconds");
	}
}
