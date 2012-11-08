package com.mmounirou.spotify.commands;

public interface Command
{
	@SuppressWarnings("serial")
	public class CommandException extends Exception
	{

		public CommandException()
		{
		}

		public CommandException(String message)
		{
			super(message);
		}

		public CommandException(Throwable cause)
		{
			super(cause);
		}

		public CommandException(String message, Throwable cause)
		{
			super(message, cause);
		}

	}

	void run() throws CommandException;
}
