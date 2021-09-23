insert into states(name) values
    ('Принята'),
    ('Отклонена'),
    ('Исполняется'),
    ('Завершена')
;
insert into categories(name) values
    ('Важная'),
    ('Обычная')
;
insert into roles(name) values
    ('Администратор'),
    ('Пользователь'),
    ('Гость')
;
insert into rules(name) values
    ('Создавать заявки'),
    ('Редактировать заявки'),
    ('Просматривать заявки'),
    ('Удалять заявки')
;
insert into roles_to_rules(roles_id, rules_id) values
    (1, 1), (1, 2), (1, 3), (1, 4),
    (2, 1), (2, 2), (2, 3),
    (3, 3)
;
insert into users(name, roles_id) values
    ('Иван', 1),
    ('Анна', 2),
    ('Николай', 2),
    ('Всевед', 3)
;
insert into items(users_id, states_id, categories_id) values
    (1, 1, 1),
    (2, 2, 2), (2, 3, 2),
    (3, 4, 1)
;
insert into comments(items_id, name) values
    (1, 'Будем счастливы сделать это для Вас.'),
    (2, 'Извините, это не наша работа.'),
    (2, 'Ну это точно не к нам.'),
    (3, 'Работаем как подорванные.'),
    (4, 'Мы всё сделали')
;
insert into attachments(items_id, "name") values
    (1, 'Заявка принята.doc'),
    (2, ''),
    (3, ''),
    (4, 'Акт выполненных работ.doc'),
    (1, 'Уточняющие вопросы.pdf')
;
