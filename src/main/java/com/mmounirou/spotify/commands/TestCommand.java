package com.mmounirou.spotify.commands;

import com.google.common.eventbus.EventBus;
import com.mmounirou.spotify.commands.RunCommand.RunMode;

public class TestCommand implements Command
{

	private EventBus m_eventBus;

	public TestCommand(EventBus eventBus)
	{
		m_eventBus = eventBus;
	}

	public void run() throws CommandException
	{
		new RunCommand(m_eventBus, RunMode.TEST);
	}

}
