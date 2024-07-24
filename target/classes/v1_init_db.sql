create table books
(id serial primary key, title varchar(50) not null, author varchar(50) not null, library_id serial, is_using_now boolean);

create table library
(id serial primary key, title varchar(50));

create table reader
(id serial primary key, name varchar(50), has_been_to_the_library boolean);

create table rent
(id serial primary key, reader_id serial, book_id serial, library_id serial);