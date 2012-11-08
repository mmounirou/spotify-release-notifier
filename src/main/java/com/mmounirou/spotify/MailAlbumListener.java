package com.mmounirou.spotify;

import java.util.Comparator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.mmounirou.spotify.AlbumReleaseChecker.EndEvent;
import com.mmounirou.spotify.AlbumReleaseChecker.NewAlbumEvent;
import com.mmounirou.spotify.AlbumReleaseChecker.StartEvent;
import com.mmounirou.spoty4j.core.Album;
import com.mmounirou.spoty4j.core.Artist;

public class MailAlbumListener
{

	private Properties properties;
	private Multimap<Artist, Album> albums;

	public MailAlbumListener(Properties properties)
	{
		this.properties = properties;
	}

	@Subscribe
	public void receiveStart(StartEvent startEvent)
	{
		albums = HashMultimap.create();
	}

	@Subscribe
	public void receiveNewAlbum(NewAlbumEvent newAlbumEvent)
	{
		albums.put(newAlbumEvent.getAlbum().getArtist(), newAlbumEvent.getAlbum());
	}

	@Subscribe
	public void receiveEnd(EndEvent endEvent) throws EmailException
	{
		// TODO make the mail server configuration more generic

		HtmlEmail email = new HtmlEmail();
		String authpwd = properties.getProperty("sender.password");
		String fromMail = properties.getProperty("sender.email");
		String toName = properties.getProperty("receiver.email");

		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(fromMail, authpwd));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setFrom(fromMail, "Spotify Album Notifier");
		email.addTo(toName);
		email.setTLS(true);

		email.getMailSession().getProperties().put("mail.smtps.auth", "true");
		email.getMailSession().getProperties().put("mail.debug", "true");
		email.getMailSession().getProperties().put("mail.smtps.port", "587");
		email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
		email.getMailSession().getProperties().put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
		email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
		email.setSubject("[Spotify Release] New Released Albums");

		email.setHtmlMsg(buildHtmlMessage());

		email.send();
	}

	private String buildHtmlMessage()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>\n");
		Comparator<Artist> comparator = new Comparator<Artist>()
		{
			public int compare(Artist o1, Artist o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		};
		Set<Artist> artistsSorted = Sets.newTreeSet(comparator);
		artistsSorted.addAll(albums.keySet());

		for (Artist artist : artistsSorted)
		{
			builder.append(String.format("<b><a href=\"%s\">%s</a></b></br>\n<ul\n", artist.getHref(), artist.getName()));

			for (Album album : albums.get(artist))
			{
				builder.append(String.format("    <li><a href=\"%s\">%s</a></li>\n", album.getHref(), album.getName()));
			}

			builder.append("</ul>\n");
		}
		builder.append("</body></html>");
		return builder.toString();
	}
}
