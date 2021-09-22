create table groups(
    id serial primary key,
    name varchar(255)
);
create table students(
    id serial primary key,
    name varchar(255),
    groups_id int references groups(id)
);
