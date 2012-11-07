create table t_artists (uri varchar(80) not null , name varchar(255) , unique(uri));
create table t_albums  (uri varchar(80) not null , name varchar(255) , albumartist varchar(80) not null, unique(uri) );
create table t_tracks  (uri varchar(80) not null , name varchar(255) , trackartist varchar(80) not null, trackalbum varchar(80) not null, unique(uri));