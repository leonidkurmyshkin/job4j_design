create table teens (
	id serial primary key,
	name varchar(255),
	gender char(1)
);
insert into teens(name, gender) values
	('Иван', 'м'),
	('Олег', 'м'),
	('Павел', 'м'),
	('Аполлинария', 'ж'),
	('Мария', 'ж'),
	('Ирина', 'ж')
;
select *
	from teens as m cross join teens as f
	where m.gender = 'м' and f.gender = 'ж'
;