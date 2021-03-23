create table authorities (id bigint not null,
authority varchar(255),
username varchar(255), primary key (id))
engine=InnoDB;

create table hibernate_sequence (next_val bigint) engine=InnoDB;

insert into hibernate_sequence values ( 1 );
create table users (id bigint not null, avatar longtext, email_address varchar(255),
enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20),
username varchar(20), primary key (id)) engine=InnoDB;

create table verification_token (id bigint not null,
expiry_date datetime, token varchar(255),
user_id bigint,
primary key (id)) engine=InnoDB;

create table writerposts (id bigint not null,
 content longtext, creation_date datetime,
 intro longtext, status varchar(255), title varchar(255),
 user_id bigint, primary key (id)) engine=InnoDB;

alter table authorities add constraint FKhjuy9y4fd8v5m3klig05ktofg foreign key (username) references users (id);

alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id);

alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id);
create table authorities (id bigint not null, authority varchar(255), username varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table authorities add constraint FKhjuy9y4fd8v5m3klig05ktofg foreign key (username) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date date, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content is mandatory! longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
create table authorities (id bigint not null, authority varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table tags (id bigint not null, tag varchar(255), primary key (id)) engine=InnoDB
create table user_authority (user_id bigint not null, auth_id bigint not null) engine=InnoDB
create table users (id bigint not null, avatar longtext, email_address varchar(255), enabled bit, password varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), username varchar(20), primary key (id)) engine=InnoDB
create table verification_token (id bigint not null, expiry_date datetime, token varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts (id bigint not null, content longtext, creation_date datetime, intro longtext, is_enabled bit, status varchar(255), title varchar(255), user_id bigint, primary key (id)) engine=InnoDB
create table writerposts_tags (post_id bigint not null, tag_id bigint not null, primary key (post_id, tag_id)) engine=InnoDB
alter table user_authority add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq foreign key (auth_id) references authorities (id)
alter table user_authority add constraint FKhi46vu7680y1hwvmnnuh4cybx foreign key (user_id) references users (id)
alter table verification_token add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users (id)
alter table writerposts add constraint FK26ksub4ukoimr66c5fu07cmx3 foreign key (user_id) references users (id)
alter table writerposts_tags add constraint FKlnj5ewrdwsvpky5njr1lft4b7 foreign key (tag_id) references tags (id)
alter table writerposts_tags add constraint FKsim27vq1q63re0qudldn23hu foreign key (post_id) references writerposts (id)
