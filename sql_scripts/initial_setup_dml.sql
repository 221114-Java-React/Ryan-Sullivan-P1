insert into ers.user_roles (role_id, role_text)
values
	('1', 'admin'),
	('2', 'manager'),
	('3', 'employee');
	
insert into ers.users (user_id,
						username,
						email,
						password,
						given_name,
						surname,
						role_id)
values
	('1', 'admin', 'admin@email.com', 'admin', 'admin', 'admin', '1'),
	('2', 'Ryan', 'ryan@email.com', '12345678', 'Ryan', 'Sullivan', '2');
	
insert into ers.ticket_types (type_id, type_text)
values
	('1', 'LODGING'),
	('2', 'TRAVEL'),
	('3', 'FOOD'),
	('4', 'OTHER');
	
insert into ers.ticket_statuses (status_id, status)
values
	('1', 'PENDING'),
	('2', 'APPROVED'),
	('3', 'DENIED');
	