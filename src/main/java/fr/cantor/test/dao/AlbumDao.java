package fr.cantor.test.dao;

import java.sql.Connection;

import com.mmounirou.spoty4j.core.Track;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

import fr.cantor.test.datamodel.Albums;
import fr.cantor.test.datamodel.query.QAlbums;

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
