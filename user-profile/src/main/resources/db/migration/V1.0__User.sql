CREATE TABLE users
(
    id          bigserial NOT NULL
        CONSTRAINT users_pkey
            PRIMARY KEY,
    first_name  VARCHAR(50),
    last_name  VARCHAR(50),
    email  VARCHAR(255) UNIQUE
);