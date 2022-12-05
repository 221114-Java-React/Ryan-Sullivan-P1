
insert into ers.user_roles (api_name, label_name)
values 
('ADMIN', 'Administrator'),
('MANAGER', 'Finance Manager'),
('EMPLOYEE', 'Employee');

	
insert into ers.users (user_id, username, email, password_hash, given_name, surname, role_id, is_active)
values
('75508d95-8919-444e-bd5e-56537b61ec88',
'admin',
'admin@email.com',
'84977e16949f28fad8bfd56df8689fd085d9da21580f2bc6c867e2d1720777cc',
'Ryan',
'Sullivan',
'ADMIN',
true),

('75508d95-8919-444e-bd5e-56537b61ec89',
'jdoe',
'jdoe@email.com',
'84977e16949f28fad8bfd56df8689fd085d9da21580f2bc6c867e2d1720777cc',
'Jane',
'Doe',
'MANAGER', 
true); -- password for both is admin123

insert into ers.ticket_types (api_name, label_name)
values
('LODGING', 'Lodging'),
('TRAVEL', 'Travel'),
('FOOD', 'Food'),
('OTHER', 'Other');
