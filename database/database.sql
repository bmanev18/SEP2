-- create schema sep2_database;
set schema 'sep2_database';

create domain username as varchar(15) not null;

create table users
(
    firstName varchar(50) not null,
    lastName  varchar(50) not null,
    username  username unique,
    password  varchar(8)  not null,
    primary key (username)
);


create table chats
(
    id          serial,
    name        varchar(30) not null,
    themeColour varchar(10),
    primary key (id)
);

create table messages
(
    sender   username,
    toChat   integer,
    text     varchar(500),
    datetime timestamp,
    primary key (sender, toChat, datetime),
    foreign key (sender) references users (username),
    foreign key (toChat) references chats (id)
);

create table receivers
(
    username username,
    chat     integer not null,
    primary key (username, chat),
    foreign key (username) references users (username),
    foreign key (chat) references chats (id)
);

/*Messages(sender, toChat, text, timeStamp)
PK: sender, toChat, timeStamp
FK: sender ref. Users(username)
FK: toChat ref. Chat(id)
*/
