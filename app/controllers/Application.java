package controllers;

import models.Artist;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.SecureSocial;
import views.html.index;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.mmounirou.spoty4j.api.Search;

public class Application extends Controller
{
	public static class ArtistName
	{
		@Required
		public String artistName;
	}

	public static class LastFmName
	{
		@Required
		public String username;
	}

	@SecureSocial.Secured
	public static Result index()
	{
		return ok(index.render(Artist.all(), form(ArtistName.class), form(LastFmName.class)));
	}

	public static Result subscribeArtist()
	{
		Form<ArtistName> form = form(ArtistName.class).bindFromRequest();
		if (form.hasErrors())
		{
			return badRequest(index.render(Artist.all(), form, form(LastFmName.class)));
		}

		ArtistName data = form.get();
		Logger.debug("Subscribe to " + data.artistName);

		ImmutableList<Artist> subscribeArtists = getArtistFromSpotifyAndDB(data);
		for (Artist artist : subscribeArtists)
		{
			Logger.debug("subscribe to href = " + artist);
		}
		return index();
	}

	private static ImmutableList<Artist> getArtistFromSpotifyAndDB(ArtistName data)
	{
		ImmutableList<com.mmounirou.spoty4j.core.Artist> spotifyArtists = Search.searchArtist(data.artistName);
		return FluentIterable.from(spotifyArtists).transform(new Function<com.mmounirou.spoty4j.core.Artist, Artist>()
		{
			public Artist apply(com.mmounirou.spoty4j.core.Artist artist)
			{
				Artist dbArtist = Artist.byHref(artist.getHref());
				if (dbArtist == null)
				{
					dbArtist = new Artist(artist.getName(), artist.getHref());
					// save in database
					Artist.create(dbArtist);
				}
				return dbArtist;
			}
		}).toImmutableList();
	}

	public static Result unSubscribeArtist(long id)
	{
		return null;
	}

	public static Result subscribeArtistFromLastFm()
	{
		Form<LastFmName> form = form(LastFmName.class).bindFromRequest();
		if (form.hasErrors())
		{
			return badRequest(index.render(Artist.all(), form(ArtistName.class), form));
		} else
		{
			LastFmName data = form.get();
			Logger.info(data.username);
			return index();
		}
	}

}