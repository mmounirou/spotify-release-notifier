package com.mmounirou.spotify.dao;

import java.sql.Connection;

import com.mmounirou.spotify.datamodel.Albums;
import com.mmounirou.spotify.datamodel.query.QAlbums;
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

	public boolean exist(String href)
	{
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QAlbums talbums = QAlbums.tAlbums;
		return query.from(talbums).where(talbums.uri.eq(href)).exists();
	}

	public void addAlbum(Albums album)
	{
		SQLInsertClause insertClause = new SQLInsertClause(m_connection, new SQLiteTemplates(), QAlbums.tAlbums);
		insertClause.populate(album).execute();
	}

}
