drop table if exists books;

create table books (id uuid not null constraint books_pk primary key, title text not null);

insert into books values('d341984f-338d-4476-b999-ba8d32d06219', 'Scala 3');