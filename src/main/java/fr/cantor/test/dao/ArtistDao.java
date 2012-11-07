package fr.cantor.test.dao;

import java.sql.Connection;

import com.google.common.collect.ImmutableList;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

import fr.cantor.test.datamodel.Artists;
import fr.cantor.test.datamodel.query.QArtists;

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

	public void addArtists(Iterable<Artists> artists)
	{
		for ( Artists artist : artists )
		{
			addArtist(artist);
		}
	}

	private void addArtist(Artists artist)
	{
		SQLInsertClause insertClause = new SQLInsertClause(m_connection, new SQLiteTemplates(), QArtists.tArtists);
		insertClause.populate(artist).execute();
	}

	public ImmutableList<Artists> all()
	{
		// TODO return an Iterable
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QArtists tartists = QArtists.tArtists;
		return ImmutableList.copyOf(query.from(tartists).list(tartists));
	}

	public boolean exist(String href)
	{
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QArtists tartists = QArtists.tArtists;
		return query.from(tartists).where(tartists.uri.eq(href)).exists();
	}

}
