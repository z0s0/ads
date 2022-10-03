-- this is to rapidly setup it locally
create user ads_dev with password 'ads_dev';
create database ads_dev;
grant all privileges on database ads_dev to ads_dev;


create table if not exists users (
  id serial primary key,
  username text not null
);

create unique index users_uidx on users (username);

create table ads(
  id serial primary key,
  body text not null
);

create table impressions(
  id serial primary key,
  user_id serial references users(id),
  ad_id serial references ads(id),
  show_time TIMESTAMP not null
);

create index on impressions(ad_id, user_id);

create table clicks(
  id serial primary key,
  user_id serial references users(id),
  ad_id serial references ads(id),
  action_time TIMESTAMP not null
);

create index on clicks (user_id, ad_id);

