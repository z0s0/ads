insert into ads(body) values('best guitar');
insert into ads(body) values ('best car');
insert into ads(body) values('best guitar2');
insert into ads(body) values ('best car2');
insert into ads(body) values('best guitar3');
insert into ads(body) values ('best car3');
insert into ads(body) values('best guitar4');
insert into ads(body) values ('best car5');
insert into ads(body) values('best guitar6');
insert into ads(body) values ('best car7');

insert into users(username) values ('user1');
insert into users(username) values ('user2');
insert into users(username) values ('user3');
insert into users(username) values ('user4');

insert into clicks(user_id, ad_id, action_time)
  values (1, 1, current_timestamp ),
         (2, 2, current_timestamp),
         (1, 2, current_timestamp),
         (1, 3, current_timestamp),
         (2, 3, current_timestamp),
         (1, 3, current_timestamp);

insert into impressions(user_id, ad_id, show_time)
  values (1, 1, current_timestamp ),
         (2, 2, current_timestamp),
         (1, 2, current_timestamp);