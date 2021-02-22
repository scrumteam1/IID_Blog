create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table registered_visitor (id bigint not null, email_address varchar(255), first_name varchar(20), gender varchar(255), is_writer bit, last_name varchar(20), password varchar(255), username varchar(20), primary key (id)) engine=InnoDB;
