

drop table if exists user_authority;
drop table if exists writerposts_tags;
drop table if exists authorities;
drop table if exists hibernate_sequence;
drop table if exists tags;
drop table if exists verification_token;
drop table if exists writerposts;
drop table if exists users;


create table if not exists authorities
(
    id bigint not null
        primary key,
    authority varchar(255) null
);

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists tags
(
    id bigint not null
        primary key,
    tag varchar(255) null
);

create table if not exists users
(
    id bigint not null
        primary key,
    avatar longtext null,
    email_address varchar(255) null,
    enabled bit null,
    password varchar(255) null,
    first_name varchar(20) null,
    gender varchar(255) null,
    is_writer bit null,
    last_name varchar(20) null,
    username varchar(20) null
);

create table if not exists user_authority
(
    user_id bigint not null,
    auth_id bigint not null,
    constraint FKhi46vu7680y1hwvmnnuh4cybx
        foreign key (user_id) references users (id),
    constraint FKlw8vjvnr05ipx4u5c6m3ypxoq
        foreign key (auth_id) references authorities (id)
);

create table if not exists verification_token
(
    id bigint not null
        primary key,
    expiry_date datetime null,
    token varchar(255) null,
    user_id bigint null,
    constraint FK3asw9wnv76uxu3kr1ekq4i1ld
        foreign key (user_id) references users (id)
);

create table if not exists writerposts
(
    id bigint not null
        primary key,
    content longtext null,
    creation_date datetime not null,
    intro longtext null,
    is_enabled bit null,
    status varchar(255) null,
    title varchar(255) null,
    user_id bigint null,
    constraint FK26ksub4ukoimr66c5fu07cmx3
        foreign key (user_id) references users (id)
);

create table if not exists writerposts_tags
(
    post_id bigint not null,
    tag_id bigint not null,
    primary key (post_id, tag_id),
    constraint FKlnj5ewrdwsvpky5njr1lft4b7
        foreign key (tag_id) references tags (id),
    constraint FKsim27vq1q63re0qudldn23hu
        foreign key (post_id) references writerposts (id)
);

