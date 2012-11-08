package com.mmounirou.spotify.commands;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.commands.RunCommand.RunMode;
import com.mmounirou.spotify.dao.ArtistDao;
import com.mmounirou.spotify.dao.DBUtils;
import com.mmounirou.spotify.datamodel.Artists;
import com.mmounirou.spotify.listener.EventListener.ListCommandEndEvent;
import com.mmounirou.spotify.listener.EventListener.ListCommandStartEvent;
import com.mmounirou.spotify.listener.EventListener.NewArtistEvent;
import com.mmounirou.spoty4j.core.Artist;

public class ListCommand implements Command
{

	private EventBus eventBus;

	public ListCommand(EventBus eventBus)
	{
		this.eventBus = eventBus;
	}

	public void run() throws CommandException
	{
		eventBus.post(ListCommandStartEvent.of(this));
		Connection connection = null;
		try
		{
			connection = DBUtils.connectToDb();
			ImmutableList<Artists> all = new ArtistDao(connection).all();
			for (Artists artists : all)
			{
				eventBus.post(NewArtistEvent.of(new Artist(artists.getUri(), artists.getName()), RunMode.LIST));
			}
		} catch (SQLException e)
		{
			throw new CommandException(e);
		} finally
		{
			eventBus.post(ListCommandEndEvent.of(this));
		}

	}

}
