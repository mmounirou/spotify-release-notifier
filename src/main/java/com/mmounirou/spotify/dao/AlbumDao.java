package com.mmounirou.spotify.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmounirou.spotify.commons.util.ListUtils;
import com.mmounirou.spotify.datamodel.Albums;
import com.mmounirou.spotify.datamodel.query.QAlbums;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class AlbumDao
{
	private static final int MAX_ELMT_BY_QUERY = 500;

	private Connection m_connection;

	public AlbumDao(Connection connection)
	{
		m_connection = connection;
	}

	public FluentIterable<String> exist(List<String> trackHrefs)
	{

		Function<List<String>, Iterable<String>> filterExistingAlbums = new Function<List<String>, Iterable<String>>()
		{

			@Nullable
			public Iterable<String> apply(@Nullable List<String> splittedAlbums)
			{
				SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
				QAlbums tartists = QAlbums.tAlbums;
				return query.from(tartists).where(tartists.uri.in(splittedAlbums)).listDistinct(tartists.uri);
			}
		};
		return FluentIterable.from(ListUtils.split(MAX_ELMT_BY_QUERY, trackHrefs)).transformAndConcat(filterExistingAlbums);

	}

	public void addAlbums(Collection<Albums> albums)
	{
		if (!albums.isEmpty())
		{
			QAlbums talbums = QAlbums.tAlbums;
			SQLInsertClause insertClause = new SQLInsertClause(m_connection, new SQLiteTemplates(), talbums);
			for (Albums album : albums)
			{
				insertClause.set(talbums.uri, album.getUri()).set(talbums.name, album.getName()).addBatch();
			}
			insertClause.execute();
		}
	}

	public Collection<Albums> addAlbumsIfNotExists(Collection<Albums> allAlbums)
	{
		Map<String, Albums> albumById = Maps.newHashMap();
		for (Albums album : allAlbums)
		{
			albumById.put(album.getUri(), album);
		}

		FluentIterable<String> alreadySeenAlbum = exist(Lists.newArrayList(albumById.keySet()));
		albumById.keySet().removeAll(alreadySeenAlbum.toImmutableList());
		Collection<Albums> insertedAlbums = albumById.values();
		addAlbums(insertedAlbums);
		return insertedAlbums;
	}

	public void deleteAll()
	{
		new SQLDeleteClause(m_connection, new SQLiteTemplates(), QAlbums.tAlbums).execute();
	}

}
