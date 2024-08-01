drop schema if exists public;
create schema public;

create table if not exists public.books
(id serial primary key, title varchar(50) not null, author varchar(50) not null, library_id integer, is_using_now boolean);

create or replace function add_book(p_title varchar, p_author varchar, p_library_id integer) returns void as $$
    begin;
        insert into books (title, author, library_id, is_using_now)
        values (p_title, p_author, p_library_id, false);
    end;
    $$ language 'sql';

create table if not exists public.library
(id serial primary key, title varchar(50));

insert into library (title) values ('King Midas Library');

select add_book('Крестный отец', 'Марио Пьюзо', 1);
select add_book('Государь', 'Никколо Макиавелли', 1);
select add_book('Клиенты на всю жизнь', 'Пол Браун', 1);
select add_book('Паттерны проектирования', 'Эрих Гамма', 1);
select add_book('Философия Java', 'Брюс Эккель', 1);
select add_book('Карьера менеджера', 'Ли Якокка', 1);

create table if not exists public.readers
(id serial primary key, name varchar(50), has_been_to_the_library boolean);

create or replace function add_reader(p_name varchar) returns void as $$
    begin;
        insert into readers (name, has_been_to_the_library)
        values (p_name, false);
    end;
$$ language 'sql';

select add_reader('Иван IV');
select add_reader('Пётр I');
select add_reader('Александр II');

create table if not exists public.librarians
(id serial primary key, name varchar(50));

create or replace function add_librarian(p_name varchar) returns void as $$
begin;
insert into librarians (name)
values (p_name);
end;
$$ language 'sql';

select add_librarian('Ибрагим');
select add_librarian('Остап');
select add_librarian('Пафнутий');
select add_librarian('Венеамин');

create table if not exists public.rent
(id serial primary key, reader_id integer, book_id integer, library_id integer);