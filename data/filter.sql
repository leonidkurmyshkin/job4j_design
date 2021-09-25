create table type(
	id serial primary key,
	name varchar(100)
);
create table product(
	id serial primary key,
	name varchar(100),
	type_id int references type(id),
	expired_date date,
	price numeric(10, 2)
);
insert into type(name) values
	('СЫР'),
	('МОЛОКО'),
	('ХЛЕБ'),
	('МАСЛО'),
	('ПЕЧЕНЬЕ'),
	('МОРОЖЕНОЕ')
;
insert into product(name, type_id, expired_date, price) values
	('Cыр Голландский', 1, date '2021-10-25', 590.90),
	('Мороженое Пломбир', 6, date '2022-02-01', 300),
	('Сыр Масдам', 1, date '2021-12-01', 780.50),
	('Сыр плавленный', 1, date '2021-09-17', 500),
	('Мороженое сливочное', 6, date '2022-05-05', 250),
	('Хлеб Батон', 3, date '2021-09-10', 35),
	('Печенье Завитки', 5, '2021-09-15', 300),
	('Молоко цельное', 2, date '2021-10-25', 35)
;
select p.name, p.expired_date, p.price
	from product as p join type as t on p.type_id = t.id
	where t.name = 'СЫР'
;
select name, expired_date, price
	from product
	where lower(name) like '%мороженое%'
;
select name, expired_date, price
	from product
	where expired_date < CURRENT_DATE
;
select name, expired_date, price
	from product
	where price = (select max(price) from product)
;
select t.name as type_name, count(t.id) as number
	from type as t join product as p on t.id = p.type_id
	group by t.id
;
select p.name, p.expired_date, p.price
	from product as p join type as t on p.type_id = t.id
	where t.name = 'СЫР' or t.name = 'МОЛОКО'
;
select t.name as type_name
	from type as t join product as p on t.id = p.type_id
	group by t.id
	having count(t.id) < 10;
;
select p.name as product_name, t.name as type_name, p.expired_date, p.price
	from product as p join type as t on p.type_id = t.id
;