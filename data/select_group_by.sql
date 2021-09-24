create table devices(
    id serial primary key,
    name varchar(255),
    price float
);

create table people(
    id serial primary key,
    name varchar(255)
);

create table devices_people(
    id serial primary key,
    device_id int references devices(id),
    people_id int references people(id)
);
insert into devices(name, price) values
	('Ноутбук', 150000),
	('Мини ПК', 70899),
	('Гарнитура беспроводная', 3650),
	('TV', 35000),
	('Цифровой фотоаппарат', 8900)
;
insert into people(name) values
	('Соколов'),
	('Петров'),
	('Водкин'),
	('Маяковский')
;
insert into devices_people(device_id, people_id) values
	(1, 1), (1, 2), (1, 4),
	(2, 3),
	(3, 1), (3, 2), (3, 3), (3, 4),
	(4, 1), (4, 2),
	(5, 2), (5, 3)
;
select avg(price) as avg_price from devices;
select p.name, avg(d.price) as avg_price
	from people as p join devices_people as d_p on p.id = d_p.people_id
	join devices as d on d.id = d_p.device_id
	group by p.name
;
select p.name, avg(d.price) as avg_price
	from people as p join devices_people as d_p on p.id = d_p.people_id
	join devices as d on d.id = d_p.device_id
	group by p.name
	having avg(d.price) > 50000
;