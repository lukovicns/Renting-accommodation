insert into country (country_id, country_code, country_name) values (1, 'SRB', 'Serbia');
insert into country (country_id, country_code, country_name) values (2, 'GER', 'Germany');
insert into country (country_id, country_code, country_name) values (3, 'SLO', 'Slovenia');

insert into city (city_id, country_id, name, zipcode) values (1, 1, 'Novi Sad', '21000');
insert into city (city_id, country_id, name, zipcode) values (2, 1, 'Beograd', '11000');

insert into accommodation_type (accommodation_type_id, accommodation_type_name) values (1, 'Hotel');
insert into accommodation_type (accommodation_type_id, accommodation_type_name) values (2, 'Bed & breakfast');
insert into accommodation_type (accommodation_type_id, accommodation_type_name) values (3, 'Apartment');

insert into accommodation_category (accommodation_category_id, accommodation_category_name) values (1, '1-5 stars');
insert into accommodation_category (accommodation_category_id, accommodation_category_name) values (2, 'Uncategorized');

insert into agent (agent_id, name, surname, password, email, city_id, street, phone, bussiness_id) values (1, 'Goran', 'Goranovic', 'passpass', 'g@g.com', 1, 'Narodnog fronta 1', '0640000000', 1);
insert into agent (agent_id, name, surname, password, email, city_id, street, phone, bussiness_id) values (2, 'Pera', 'Petrovic', 'passpass', 'p@p.com', 2, 'Novosadska 43', '0641111111', 2);

insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (1, 'Hotel Putnik', 1, 1, 'Ilije Ognjanovica 24', 'Hotel putnik se nalazi u centru Novog Sada.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (2, 'Hotel Master', 1, 1, 'Brace Popovic bb', 'Hotel se nalazi kod sajma.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (3, 'Hotel Centar', 1, 1, 'Uspenska 1', 'Hotel se nalazi u centru Novoga Sada.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (4, 'Privatni smestaj Petrovic', 1, 2, 'Svetogorska 1', 'Smestaj se nalazi u Beogradu.', 2, 2);

insert into bed_type (bed_type_id, bed_type_name) values (1, 'King size bed');
insert into bed_type (bed_type_id, bed_type_name) values (2, 'Small bed');

insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (1, 'Room 1', 1, 'Room 1 description', 1, 80, 4, 4);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (2, 'Room 2', 1, 'Room 2 description', 1, 80, 4, 4);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (3, 'Room 3', 1, 'Room 3 description', 2, 50, 2, 2);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (4, 'Smestaj Petrovic 1', 2, 'Smestaj Petrovic 1 description', 4, 30, 2, 2);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (5, 'Smestaj Petrovic 2', 2, 'Smestaj Petrovic 2 description', 4, 70, 4, 3);

insert into administrator(admin_id, name, surname, password, email) values (1, 'Admin', 'Admin', 'passpass', 'a@a.com');

insert into additional_service(additional_service_id, additional_service_name) values (1, 'Parking');
insert into additional_service(additional_service_id, additional_service_name) values (2, 'Wifi');
insert into additional_service(additional_service_id, additional_service_name) values (3, 'Breakfast');
insert into additional_service(additional_service_id, additional_service_name) values (4, 'Half-board');
insert into additional_service(additional_service_id, additional_service_name) values (5, 'Full-board');
insert into additional_service(additional_service_id, additional_service_name) values (6, 'TV');
insert into additional_service(additional_service_id, additional_service_name) values (7, 'Mini kitchen/kitchen');
insert into additional_service(additional_service_id, additional_service_name) values (8, 'Private bathroom');

insert into apartment_additional_service(apartment_additional_service_id, apartment_id, additional_service_id) values (1, 1, 1);
insert into apartment_additional_service(apartment_additional_service_id, apartment_id, additional_service_id) values (2, 1, 2);
insert into apartment_additional_service(apartment_additional_service_id, apartment_id, additional_service_id) values (3, 1, 3);
insert into apartment_additional_service(apartment_additional_service_id, apartment_id, additional_service_id) values (4, 2, 1);
insert into apartment_additional_service(apartment_additional_service_id, apartment_id, additional_service_id) values (5, 2, 2);
