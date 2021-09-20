create table chairs(
	id serial primary key,
	name varchar(255),
	style text,
	is_broken boolean,
	price numeric(10, 2)
);
insert into chairs(name, style, is_broken, price)
	values('Поэнг', 'Модерн', FALSE, 3600.28);
update chairs set is_broken = TRUE;
delete from chairs;
