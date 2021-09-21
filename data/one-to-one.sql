create table keys(
    id serial primary key,
    label varchar(255),
    weight int
);
create table locks(
    id serial primary key,
    label varchar(255),
    weight int
);
create table keys_to_locks(
    id serial primary  key,
    keys_id int references keys(id) unique,
    locks_id int references locks(id) unique
);
