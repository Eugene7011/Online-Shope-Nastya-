CREATE TABLE products(
id SERIAL PRIMARY KEY,
name VARCHAR (255),
price DOUBLE,
creation_date TIMESTAMP
);

INSERT INTO products (
id,
name,
price,
creationdate
)
VALUES ('TV',3000);

CREATE TABLE users(
id SERIAL PRIMARY KEY,
login VARCHAR (255),
password VARCHAR (255),
salt VARCHAR (255)
);