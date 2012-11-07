package fr.cantor.test.dao;

import java.sql.Connection;

import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

import fr.cantor.test.datamodel.Tracks;
import fr.cantor.test.datamodel.query.QTracks;

public class TrackDao
{

	private Connection m_connection;

	public TrackDao(Connection connection)
	{
		m_connection = connection;
	}

	public boolean exist(String href)
	{
		SQLQueryImpl query = new SQLQueryImpl(m_connection, new SQLiteTemplates());
		QTracks tTracks = QTracks.tTracks;
		return query.from(tTracks).where(tTracks.uri.eq(href)).exists();

	}

	public void addTrack(Tracks track)
	{
		SQLInsertClause insertClause = new SQLInsertClause(m_connection, new SQLiteTemplates(), QTracks.tTracks);
		insertClause.populate(track).execute();
	}

}
