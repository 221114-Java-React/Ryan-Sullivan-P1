insert into ers.users (user_id,
						username,
						email,
						password,
						given_name,
						surname,
						user_role, is_active)
values
	('75508d95-8919-444e-bd5e-56537b61ec88', 'admin', 'admin@email.com', 'admin', 'admin', 'admin', 'ADMIN', true),
	('75508d95-8919-444e-bd5e-56537b61ec89', 'Ryan', 'ryan@email.com', '12345678', 'Ryan', 'Sullivan', 'MANAGER', true);

insert into ers.users(user_id,
						username,
						email,
						password,
						given_name,
						surname,
						is_active)
values
	('75508d95-8919-444e-bd5e-56537b61ec80', 'bob', 'bob@email.com', 'asdflkjasdf', 'bob', 'burger', true);

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
