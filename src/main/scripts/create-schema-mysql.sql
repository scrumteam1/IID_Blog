create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );

create table if not exists users
(
    id bigint not null
        primary key,
    email_address varchar(255) null,
    first_name varchar(20) null,
    gender varchar(255) null,
    is_writer bit null,
    last_name varchar(20) null,
    password varchar(255) null,
    username varchar(20) null,
    enabled tinyint default 1 not null,
    constraint users_username_uindex
        unique (username)
);

create table if not exists authorities
(
    id bigint auto_increment
        primary key,
    authority varchar(255) null,
    username varchar(255) null,
    constraint ix_auth_username
        foreign key (username) references users (username)
);

