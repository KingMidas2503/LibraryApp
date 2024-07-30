-- noinspection SqlResolveForFile
create table books
(id serial primary key, title varchar(50) not null, author varchar(50) not null, library_id integer, is_using_now boolean);

create table library
(id serial primary key, title varchar(50));

create table readers
(id serial primary key, name varchar(50), has_been_to_the_library boolean);

create table librarians
(id serial primary key, name varchar(50));

create table rent
(id serial primary key, reader_id integer, book_id integer, library_id integer);