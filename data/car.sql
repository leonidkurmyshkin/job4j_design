create table body (
	id serial primary key,
	name varchar(100),
	color varchar(50)
);
create table engine (
	id serial primary key,
	name varchar(100),
	power int
);
create table transmission (
	id serial primary key,
	name varchar(100)
);
insert into body(name, color) values
	('для Vesta седан', 'белый'),
	('для Vesta седан', 'красный'),
	('для Vesta SW универсал', 'белый'),
	('для Vesta SW универсал', 'синий'),
	('для Granta седан', 'белый'),
	('для Granta седан', 'бирюзовый'),
	('для Granta лифтбек', 'фиолетовый'),
	('для Granta хэтчбек', 'белый')
;
insert into engine(name, power) values
	('1.6 л 8-кл', 90),
	('1.6 л', 106),
	('1.6 л', 113),
	('2.0 л', 150)
;
insert into transmission(name) values
	('Автомат'),
	('Механика'),
	('Вариатор')
;
create table car (
	id serial primary key,
	name varchar(255) not null,
	body_id int references body(id) not null,
	engine_id int references engine(id) not null,
	transmission_id int references transmission(id) not null
);
insert into car(name, body_id, engine_id, transmission_id) values
	('Granta седан 1.6 л 8-кл. (90 л.с.), 5МТ COMFORT, белый', 5, 1, 2),
	('Granta седан 1.6 л 8-кл. (90 л.с.), 5МТ Standart, бирюзовый', 6, 1, 2),
	('Granta лифтбек 1.6 л 16-кл. (106 л.с.), 4АТ, фиолетовый', 7, 2, 1),
	('Vesta седан 1.6 л 16-кл. (106 л.с.), 5МТ, белый', 1, 2, 2),
	('Vesta седан 1.6 л 16-кл. (113 л.с.), 4АТ COMFORT, красный', 2, 3, 1),
	('Vesta SW универсал 1.6 л 16-кл. (113 л.с.), 4АТ COMFORT, синий', 4, 3, 1)
;
select c.name, b.name as b_name, b.color, e.name as e_name, e.power, t.name as t_name
	from car as c
		join body as b on c.body_id = b.id
		join engine as e on c.engine_id = e.id
		join transmission as t on c.transmission_id = t.id
;
select b.name, b.color
	from body as b left join car as c on b.id = c.body_id
	where c.body_id is null
;
select e.name, e.power
	from engine as e left join car as c on e.id = c.engine_id
	where c.engine_id is null
;
select t.name
	from transmission as t left join car as c on t.id = c.transmission_id
	where c.transmission_id is null
;