package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Artist extends Model
{
	@Id public Long id;
	@Required public String name;
	@Required @Column(unique = true) public String href;

	public static Finder<Long, Artist> find = new Finder<Long, Artist>(Long.class, Artist.class);

	public static List<Artist> all()
	{
		return find.all();
	}

	public static void create(Artist artist)
	{
		artist.save();
	}

}
