package com.mmounirou.spotify.commands;

public class ResetCommand implements Command
{

	public void run() throws CommandException
	{
		new DropCommand(true).run();
	}

}
