create table students(
    id serial primary key,
    name varchar(255)
);
create table groups(
    id serial primary key,
    students_id int references students(id),
    name varchar(255)
);