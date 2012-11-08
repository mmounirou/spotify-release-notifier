package com.mmounirou.spotify.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.commons.sql.ConnectionUtils;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spotify.listener.Events;
import com.mmounirou.spoty4j.api.Search;
import com.mmounirou.spoty4j.core.Artist;

public class ArtistCommand implements Command
{

	private static class PrefectMatch implements Predicate<Artist>
	{
		private final String strName;

		public PrefectMatch(String name)
		{
			strName = name;
		}

		public boolean apply(@Nullable Artist input)
		{
			return StringUtils.equalsIgnoreCase(input.getName(), strName);
		}

	}

	private Iterable<String> m_strArtists;
	private EventBus m_eventBus;

	public ArtistCommand(EventBus eventBus, Iterable<String> strArtists)
	{
		m_eventBus = eventBus;
		m_strArtists = strArtists;
	}

	public void run() throws CommandException
	{

		Connection connection = null;
		try
		{
			connection = DBUtils.connectToDb();
			Collection<Artists> addedArtist = new ArtistDao(connection).addArtistIfNotExist(Lists.newArrayList(fetchSpotifyArtists(m_strArtists)));
			fireNewArtists(addedArtist);
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

	private void fireNewArtists(Collection<Artists> addedArtist)
	{
		for ( Artists artists : addedArtist )
		{
			m_eventBus.post(Events.newArtist(artists));
		}
	}

	private Iterable<Artists> fetchSpotifyArtists(Iterable<String> strArtists)
	{
		Function<String, Iterable<Artist>> querySpotifyArtist = new Function<String, Iterable<Artist>>()
		{

			@Nullable
			public Iterable<Artist> apply(@Nullable String strArtist)
			{
				return FluentIterable.from(Search.searchArtist(strArtist)).filter(new PrefectMatch(strArtist));
			}
		};

		Predicate<String> notBlankName = new Predicate<String>()
		{

			public boolean apply(@Nullable String input)
			{
				return StringUtils.isNotBlank(input);
			}
		};

		//@formatter:off
		Function<Artist, Artists> toDbArtists = new Function<Artist, Artists>()
		{
			@Nullable
			public Artists apply(@Nullable Artist input)
			{
				Artists artists = new Artists();
				artists.setName(input.getName());
				artists.setUri(input.getHref());
				return artists;
			}
		};
		
		return FluentIterable.from(FluentIterable.from(strArtists)
					  .filter(notBlankName)
					  .transformAndConcat(querySpotifyArtist)
					  .toImmutableSet())
					  .transform(toDbArtists);
	   //@formatter:on		
	}

}
