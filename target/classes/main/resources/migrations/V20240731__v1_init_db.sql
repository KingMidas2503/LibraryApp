create table if not exists public.books
(id serial primary key, title varchar(50) not null, author varchar(50) not null, library_id integer, is_using_now boolean);

insert into books (title, author, library_id, is_using_now) values ('Крестный отец', 'Марио Пьюзо', 1, false);
insert into books (title, author, library_id, is_using_now) values ('Государь', 'Никколо Макиавелли', 1, false);
insert into books (title, author, library_id, is_using_now) values ('Клиенты на всю жизнь', 'Пол Браун', 1, false);
insert into books (title, author, library_id, is_using_now) values ('Паттерны проектирования', 'Эрих Гамма', 1, false);
insert into books (title, author, library_id, is_using_now) values ('Философия Java', 'Брюс Эккель', 1, false);
insert into books (title, author, library_id, is_using_now) values ('Карьера менеджера', 'Ли Якокка', 1, false);

create table if not exists public.library
(id serial primary key, title varchar(50));

insert into library (title) values ('King Midas Library');

create table if not exists public.readers
(id serial primary key, name varchar(50), has_been_to_the_library boolean);

insert into readers (name, has_been_to_the_library) values ('Иван IV', false);
insert into readers (name, has_been_to_the_library) values ('Пётр I', false);
insert into readers (name, has_been_to_the_library) values ('Александр II', false);

create table if not exists public.librarians
(id serial primary key, name varchar(50));

insert into librarians (name) values ('Ибрагим');
insert into librarians (name) values ('Остап');
insert into librarians (name) values ('Пафнутий');
insert into librarians (name) values ('Венеамин');

create table if not exists public.rent
(id serial primary key, reader_id integer, book_id integer, library_id integer, is_active boolean);
