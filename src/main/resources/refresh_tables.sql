DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id   SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users(name)
VALUES ('name1'),
       ('name2'),
       ('name3');