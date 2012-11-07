create table t_artists (uri varchar(80) not null , name varchar(255) , unique(uri));
create table t_albums  (uri varchar(80) not null , name varchar(255) , unique(uri) );
create table t_tracks  (uri varchar(80) not null , name varchar(255));
create table t_artist_album (artists_uri varchar(80) not null ,albums_uri varchar(80) not null);
create table t_album_tracks (albums_uri varchar(80) not null ,tracks_uri varchar(80) not null);