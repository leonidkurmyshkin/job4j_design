CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values
	(1,'Ikea'),
	(2, 'Стройка'),
	(3, 'Касторама'),
	(4, 'Магнит'),
	(5, 'Пятерочка'),
	(6, 'Мвидео')
;

insert into person(id, name, company_id) values
	(1, 'Петров', 1),
	(2, 'Бобров', 1),
	(3, 'Машкин', 5),
	(4, 'Дудкин', 5),
	(5, 'Сидоркин', 5),
	(6, 'Колосков', 2)
;

SELECT p.name, c.name AS company
FROM person AS p JOIN company AS c 
ON p.company_id = c.id AND c.id <> 5;

WITH company_count AS (
	SELECT	c.name,	COUNT(*)
	FROM company as c JOIN person AS p
	ON c.id = p.company_id
	GROUP BY c.id
)
SELECT * 
FROM company_count
WHERE count = (
	SELECT MAX(count)
	FROM company_count
);




	
	