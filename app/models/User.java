package models;

import play.db.ebean.Model;

public class User extends Model
{
	public String login;
	public String password;
}
