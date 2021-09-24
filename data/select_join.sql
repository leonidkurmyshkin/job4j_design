create table boxes(
	id serial primary key,
	color varchar(100),
	capacity real
);
create table things(
	id serial primary key,
	name varchar(255),
	color varchar(100),
	volume real,
	boxes_id int references bo(xes(id)
);
insert into boxes(color, capacity) values
	('красная', 100),
	('жёлтая', 50),
	('синяя', 10),
	('белая', 5)
;
insert into things(name, color, volume, boxes_id) values
	('Куб', 'cеро-белый', 4.5, 4),
	('Шар', 'фиолетовый', 10.7, 1),
	('Куб', 'белый', 20, 1),
	('Пирамида', 'зеленая', 20, 1),
	('Куб', 'белый', 20, 2),
	('Пирамида', 'зеленая', 20, 2),
	('Куб', 'красный', 4, 1),
	('Шар', 'желтый', 5, 2)
;
select th.name as things_name, th.color, b.color as box_color
	from things as th join boxes as b
	on th.boxes_id = b.id
;
select th.name as things_name, th.color as same_color
	from things as th join boxes as b
	on th.boxes_id = b.id and left(th.color, 3) = left(b.color, 3)
;
select th.name as things_name, th.volume, b.color as box_color, b.capacity
	from things as th join boxes as b
	on th.boxes_id = b.id and b.capacity > 80
;	
	