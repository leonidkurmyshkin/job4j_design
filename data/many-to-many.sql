create table ingredients(
    id serial primary key,
    name varchar(255),
    weight int
);
create table recipes(
    id serial primary key,
    name varchar(255)
);
create table ingredients_to_recipes(
    id serial primary key,
    ingredients_id int references ingredients(id),
    recipes_id int references recipes(id)
);
