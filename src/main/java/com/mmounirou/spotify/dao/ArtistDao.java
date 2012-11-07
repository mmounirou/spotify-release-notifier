package com.mmounirou.spotify.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spotify.datamodel.query.QArtists;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class ArtistDao
{

	private Connection m_connection;

	public ArtistDao(Connection connection)
	{
		m_connection = connection;
	}

	public long getArtistCount()
	{
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QArtists tartists = QArtists.tArtists;
		return query.from(tartists).count();
	}

	public void addArtists(Collection<Artists> artists)
	{
		if ( !artists.isEmpty() )
		{
			long begin = System.currentTimeMillis();
			QArtists tartists = QArtists.tArtists;
			SQLInsertClause insertClause = new SQLInsertClause(m_connection, new SQLiteTemplates(), tartists);
			for ( Artists artist : artists )
			{
				//@formatter:off
				insertClause.set(tartists.uri, artist.getUri())
							.set(tartists.name, artist.getName())
							.addBatch();
				//@formatter:on
			}
			insertClause.execute();

			long end = System.currentTimeMillis();
			System.out.println("Insert artists" + (begin - end) / 1000);
		}

	}

	public ImmutableList<Artists> all()
	{
		// TODO return an Iterable
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QArtists tartists = QArtists.tArtists;
		return ImmutableList.copyOf(query.from(tartists).list(tartists));
	}

	public List<String> exist(Set<String> artistHrefs)
	{
		System.out.println(artistHrefs.size());
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QArtists tartists = QArtists.tArtists;
		return query.from(tartists).where(tartists.uri.in(artistHrefs)).listDistinct(tartists.uri);
	}

	public Collection<Artists> addArtistIfNotExist(Collection<Artists> artists)
	{
		Map<String, Artists> artistsById = Maps.newHashMap();
		for ( Artists artist : artists )
		{
			artistsById.put(artist.getUri(), artist);
		}

		List<String> existingArtists = exist(artistsById.keySet());
		artistsById.keySet().removeAll(existingArtists);
		Collection<Artists> values = artistsById.values();
		addArtists(values);
		return values;
	}

}