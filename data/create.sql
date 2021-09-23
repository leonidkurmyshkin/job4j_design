create table roles(
    id serial primary key,
    name text
);
create table rules(
    id serial primary key,
    name text
);
create table roles_to_rules(
    id serial primary key,
    roles_id int references roles(id),
    rules_id int references rules(id)
);
create table users(
    id serial primary key,
    name text,
    roles_id int references roles(id)
);
create table categories(
    id serial primary key,
    name text
);
create table states(
    id serial primary key,
    name text
);
create table items(
    id serial primary key,
    users_id int references users(id),
    states_id int references states(id),
    categories_id int references categories(id)
);
create table comments(
    id serial primary key,
    items_id int references items(id),
    name text
);
create table attachments(
    id serial primary key,
    items_id int references items(id),
    name text
);
