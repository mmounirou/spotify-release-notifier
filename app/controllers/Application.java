package controllers;

import models.Artist;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.google.common.collect.Lists;

public class Application extends Controller
{
	public static class ArtistName
	{
		@Required public String artistName;
	}

	public static class LastFmName
	{
		@Required public String username;
	}

	public static Result index()
	{
		return ok(index.render(Lists.<Artist> newArrayList(), form(ArtistName.class), form(LastFmName.class)));
	}

	public static Result subscribeArtist()
	{
		Form<ArtistName> form = form(ArtistName.class).bindFromRequest();
		if ( form.hasErrors() )
		{
			return badRequest(index.render(Lists.<Artist> newArrayList(), form, form(LastFmName.class)));
		}
		else
		{
			ArtistName data = form.get();
			Logger.info(data.artistName);
			return index();
		}
	}

	public static Result unSubscribeArtist(long id)
	{
		return null;
	}

	public static Result subscribeArtistFromLastFm()
	{
		Form<LastFmName> form = form(LastFmName.class).bindFromRequest();
		if ( form.hasErrors() )
		{
			return badRequest(index.render(Lists.<Artist> newArrayList(), form(ArtistName.class), form));
		}
		else
		{
			LastFmName data = form.get();
			Logger.info(data.username);
			return index();
		}
	}

}