package com.mmounirou.spotify.dao;

import java.sql.Connection;

import com.mmounirou.spotify.datamodel.Tracks;
import com.mmounirou.spotify.datamodel.query.QTracks;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;


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
