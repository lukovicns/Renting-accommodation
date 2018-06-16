insert into country (country_id, country_code, country_name) values (1, 'SRB', 'Serbia');
insert into country (country_id, country_code, country_name) values (2, 'GER', 'Germany');
insert into country (country_id, country_code, country_name) values (3, 'SLO', 'Slovenia');

insert into city (city_id, country_id, name, zipcode) values (1, 1, 'Novi Sad', '21000');
insert into city (city_id, country_id, name, zipcode) values (2, 1, 'Beograd', '11000');

insert into accommodation_type (accommodation_type_id, accommodation_type_name) values (1, 'hotel');
insert into accommodation_category (accommodation_category_id, accommodation_category_name) values (1, '1-5 zvezdica');

insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (1, 'Hotel Putnik', 1, 1, 'Ilije Ognjanovica 24', 'Hotel putnik se nalazi u centru Novog Sada.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (2, 'Hotel Master', 1, 1, 'Brace Popovic bb', 'Hotel se nalazi kod sajma.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (3, 'Hotel Centar', 1, 1, 'Uspenska 1', 'Hotel se nalazi u centru Novoga Sada.', 1, 1);

insert into bed_type (bed_type_id, bed_type_name) values (1, 'King size bed');

insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (1, 'Room 1', 1, 'Room 1 description', 1, 80, 4, 4);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (2, 'Room 2', 1, 'Room 2 description', 2, 80, 4, 4);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (3, 'Room 3', 1, 'Room 3 description', 1, 50, 2, 2);

insert into user (user_id, name, surname, password, email, city_id, street, phone, question, answer, max_tries) values (1, 'Test', 'Test', 'passpass', 'test@test.com', 1, 'Test street', '0640000000', 'Test question', 'Test answer', 0);
insert into agent (agent_id, name, surname, password, email, city_id, street, phone, bussiness_id) values (1, 'Goran', 'Goranovic', 'passpass', 'g@g.com', 1, 'Narodnog fronta 1', '0640000000', 2);
insert into administrator(admin_id, name, surname, password, email) values (1, 'Admin', 'Admin', 'passpass', 'a@a.com');

insert into reservation (reservation_id, user_id, apartment_id, start_date, end_date, price) values (1, 1, 1, '2018-07-16', '2018-07-26', 150);
