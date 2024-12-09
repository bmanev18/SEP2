create schema sep2_database;
set schema 'sep2_database';

create domain username as varchar(15) not null;

create table user_table
(
    firstName varchar(50) not null,
    lastName  varchar(50) not null,
    username  username unique,
    password  varchar(8)  not null,
    primary key (username)
);


create table chat_table
(
    id          serial,
    name        varchar(30) not null,
    themeColour varchar(10),
    primary key (id)
);

create table message_table
(
    id       serial,
    sender   username,
    toChat   integer,
    text     varchar(500),
    datetime timestamp,
    primary key (id),
    foreign key (sender) references user_table (username),
    foreign key (toChat) references chat_table (id)
);

create table receiver_table
(
    username username,
    chat     integer not null,
    primary key (username, chat),
    foreign key (username) references user_table (username),
    foreign key (chat) references chat_table (id)
);
