# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table artist (
  id                        bigint not null,
  name                      varchar(255),
  href                      varchar(255),
  constraint uq_artist_href unique (href),
  constraint pk_artist primary key (id))
;

create sequence artist_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists artist;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists artist_seq;

