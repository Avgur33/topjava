DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES  (100000, '2021-06-20 09:00:00','user_breakfast', 500),
        (100000, '2021-06-20 14:00:00','user_dinner',    600),
        (100000, '2021-06-20 19:00:00','user_supper',    700),
        (100001, '2021-06-18 09:00:00','admin_breakfast', 700),
        (100001, '2021-06-18 14:00:00','admin_dinner',    800),
        (100001, '2021-06-18 19:00:00','admin_supper',    900);