package com.mmounirou.spotify.commons;

import java.io.IOException;

import javax.annotation.Nullable;

import org.xml.sax.SAXException;

import com.github.mvollebregt.lastfm4j.Period;
import com.github.mvollebregt.lastfm4j.UserService;
import com.github.mvollebregt.musicmetamodel.Artist;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

public final class LastFmUtils
{
	private LastFmUtils()
	{
	}

	public static FluentIterable<String> getArtists(String username, Period period) throws IOException, SAXException
	{
		UserService service = new UserService();

		Function<Artist, String> toName = new Function<Artist, String>()
		{

			@Nullable
			public String apply(@Nullable Artist input)
			{
				return input.getName();
			}
		};
		return FluentIterable.from(service.getTopArtists(username, period)).transform(toName);
	}
}
