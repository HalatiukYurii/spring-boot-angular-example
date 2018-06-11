CREATE TABLE users (
    id       BINARY(16)   PRIMARY KEY,
    username VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    role     VARCHAR(256) NOT NULL,
    email    VARCHAR(256)
);

CREATE TABLE client_details (
    client_id               VARCHAR(255) NOT NULL,
    client_secret           VARCHAR(255),
    scope                   VARCHAR(255),
    resource_ids            VARCHAR(255),
    authorized_grant_types  VARCHAR(255),
    redirect_uri            VARCHAR(255),
    auto_approve            VARCHAR(255),
    authorities             VARCHAR(255),
    access_token_validity   INT,
    refresh_token_validity  INT,
    additional_information  VARCHAR(255),
    web_server_redirect_uri VARCHAR(255)
);


CREATE TABLE receipts (
	id    BINARY(16)   PRIMARY KEY,
	buyer VARCHAR(256) NOT NULL,
    date  timestamp    NOT NULL
);

CREATE TABLE products (
	id    BINARY(16)   PRIMARY KEY,
	name  VARCHAR(256) NOT NULL UNIQUE,
	price DOUBLE       NOT NULL
);

CREATE TABLE receipt_products (
	receipt_id BINARY(16) NOT NULL,
	product_id BINARY(16) NOT NULL,
	FOREIGN KEY (receipt_id)
        REFERENCES receipts(id),
    FOREIGN KEY (product_id)
	    REFERENCES products(id)
);

INSERT INTO client_details (client_id, client_secret, authorities, authorized_grant_types, resource_ids, scope) VALUES
  ('user-client', '{bcrypt}$2a$04$OQjKzCTOgHnsIE3Aq1YtM.M1.gyO30Dna06VKYsi85SAem5k0FiaS', 'TRUSTED_CLIENT', 'password',
   'web-app', 'read,write');

INSERT INTO users (id, username, password, role, email) VALUES
  (unhex(replace(uuid(), '-', '')), 'administrator',
   '{bcrypt}$2a$10$kM0FeyUvvT3UrtEm3y/WAueojWeBO1vwGZFxXxdqfWZgZoMXvDeR.', 'ADMINISTRATOR', 'asd@gmail.com');