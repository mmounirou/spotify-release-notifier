package com.mmounirou.spotify.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.mmounirou.spotify.datamodel.query.QAlbums;
import com.mmounirou.spoty4j.core.Album;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class AlbumDao
{

	private Connection m_connection;

	public AlbumDao(Connection connection)
	{
		m_connection = connection;
	}

	public List<String> exist(Set<String> keySet)
	{
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QAlbums talbums = QAlbums.tAlbums;
		return query.from(talbums).where(talbums.uri.in(keySet)).listDistinct(talbums.uri);

	}

	public void addAlbums(Collection<Album> albums)
	{
		if ( !albums.isEmpty() )
		{
			QAlbums talbums = QAlbums.tAlbums;
			SQLInsertClause insertClause = new SQLInsertClause(m_connection, new SQLiteTemplates(), talbums);
			for ( Album album : albums )
			{
				insertClause.set(talbums.uri, album.getHref()).set(talbums.name, album.getName()).addBatch();
			}
			insertClause.execute();
		}
	}
	
	public Collection<Album> addAlbumsIfNotExists(Collection<Album> allAlbums)
	{
		Map<String, Album> albumById = Maps.newHashMap();
		for ( Album album : allAlbums )
		{
			albumById.put(album.getHref(), album);
		}

		List<String> alreadySeenAlbum = exist(albumById.keySet());
		albumById.keySet().removeAll(alreadySeenAlbum);
		Collection<Album> insertedAlbums = albumById.values();
		addAlbums(insertedAlbums);
		return insertedAlbums;
	}

}
