DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS ticket_statuses CASCADE;
DROP TABLE IF EXISTS ticket_types CASCADE;
DROP TABLE IF EXISTS user_role cascade;



CREATE TYPE user_role AS ENUM ('ADMIN', 'MANAGER', 'EMPLOYEE');

CREATE TABLE users (
	user_id VARCHAR PRIMARY KEY,
	username VARCHAR unique not null,
	email VARCHAR UNIQUE NOT NULL,
	password VARCHAR NOT NULL,
	given_name VARCHAR NOT NULL,
	surname VARCHAR NOT NULL,
	is_active BOOLEAN DEFAULT false,
	user_role user_role DEFAULT 'EMPLOYEE'
);

CREATE TABLE ticket_statuses (
	status_id VARCHAR PRIMARY KEY,
	status VARCHAR UNIQUE
);

CREATE TABLE ticket_types (
	type_id VARCHAR PRIMARY KEY,
	type_text VARCHAR UNIQUE
);

CREATE TABLE tickets (
	ticket_id VARCHAR PRIMARY KEY,
	amount NUMERIC(6, 2) NOT NULL,
	submitted TIMESTAMP NOT NULL,
	resolved TIMESTAMP,
	description VARCHAR NOT NULL, 
	receipt BYTEA,
	payment_id VARCHAR,
	author_id VARCHAR REFERENCES users(user_id) NOT NULL,
	resolver_id VARCHAR REFERENCES users(user_id),
	status_id VARCHAR REFERENCES ticket_statuses(status_id),
	type_id VARCHAR REFERENCES ticket_types(type_id)
);

