package com.mmounirou.spotify.commands;

import com.google.common.eventbus.EventBus;

public class ResetCommand implements Command
{

	private EventBus m_eventBus;

	public ResetCommand(EventBus eventBus)
	{
		m_eventBus = eventBus;
	}

	public void run() throws CommandException
	{
		new DropCommand(m_eventBus, true).run();
	}

}
