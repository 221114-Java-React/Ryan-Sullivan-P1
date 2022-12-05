DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS ticket_statuses CASCADE;
DROP TABLE IF EXISTS ticket_types CASCADE;
drop table if exists registrations cascade;
drop table if exists user_roles cascade;
drop type if exists ticket_status cascade;



create table user_roles (
	api_name varchar primary key,
	label_name varchar
);

CREATE TABLE users (
	user_id VARCHAR PRIMARY KEY,
	username VARCHAR unique not null,
	email VARCHAR UNIQUE NOT NULL,
	password_hash varchar NOT NULL,
	given_name VARCHAR NOT NULL,
	surname VARCHAR NOT NULL,
	is_active BOOLEAN default false,
	role_id varchar references user_roles(api_name) not null
);

create table registrations (
	request_id VARCHAR PRIMARY KEY,
	username VARCHAR unique not null,
	email VARCHAR UNIQUE NOT NULL,
	password_hash varchar NOT NULL,
	given_name VARCHAR NOT NULL,
	surname VARCHAR NOT NULL,
	role_id varchar references user_roles(api_name) not null
);

CREATE TYPE ticket_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

CREATE TABLE ticket_types (
	api_name varchar primary key,
	label_name varchar not null
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
	status ticket_status default 'PENDING' not NULL,
	ticket_type VARCHAR REFERENCES ticket_types(api_name) not null
);