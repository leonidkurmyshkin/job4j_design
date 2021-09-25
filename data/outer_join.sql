create table departments (
	id serial primary key,
	name varchar(150)
);
create table employers (
	id serial primary key,
	name varchar(150),
	dep_id int references departments(id)
);
insert into departments(name) values
	('Бухгалтерия'),
	('Отдел безопасности'),
	('IT'),
	('Одел грузоперевозок'),
	('Управление')
;
insert into employers(name, dep_id) values
	('Попова', 1),
	('Соколова', 1),
	('Брекоткина', 1),
	('Ершов', 2),
	('Умелый', 3),
	('Бывалый', 3),
	('Большой', 5)
;
select *
	from departments as d left join employers as e on e.dep_id = d.id
	where e.dep_id is null
;
select *
	from departments as d left join employers as e on e.dep_id = d.id
;
select *
	from employers as e right join departments as d on e.dep_id = d.id
;

