insert into country (country_id, country_code, country_name) values (1, 'SRB', 'Serbia');
insert into country (country_id, country_code, country_name) values (2, 'GER', 'Germany');
insert into country (country_id, country_code, country_name) values (3, 'SLO', 'Slovenia');

insert into city (city_id, country_id, name, zipcode) values (1, 1, 'Novi Sad', '21000');
insert into city (city_id, country_id, name, zipcode) values (2, 1, 'Beograd', '11000');

insert into accommodation_type (accommodation_type_id, accommodation_type_name, status) values (1, 'Hotel', 'ACTIVE');
insert into accommodation_type (accommodation_type_id, accommodation_type_name, status) values (2, 'Bed & breakfast', 'ACTIVE');
insert into accommodation_type (accommodation_type_id, accommodation_type_name, status) values (3, 'Apartment', 'ACTIVE');

insert into accommodation_category (accommodation_category_id, accommodation_category_name, status) values (1, '1-5 stars', 'ACTIVE');
insert into accommodation_category (accommodation_category_id, accommodation_category_name, status) values (2, 'Uncategorized', 'ACTIVE');

insert into agent (agent_id, name, surname, password, email, city_id, street, phone, bussiness_id, status) values (1, 'Goran', 'Goranovic', 'passpass', 'g@g.com', 1, 'Narodnog fronta 1', '0640000000', 1, 'WAITING');
insert into agent (agent_id, name, surname, password, email, city_id, street, phone, bussiness_id, status) values (2, 'Pera', 'Petrovic', 'passpass', 'p@p.com', 2, 'Novosadska 43', '0641111111', 2, 'WAITING');
insert into agent (agent_id, name, surname, password, email, city_id, street, phone, bussiness_id, status) values (3, 'Stefan', 'Stefanovic', 'passpass', 's@s.com', 2, 'Kralja Petra 1', '0642222222', 3, 'WAITING');

insert into administrator(admin_id, name, surname, password, email) values (1, 'Admin', 'Admin', 'passpass', 'a@a.com');

insert into user (user_id, name, surname, password, email, city_id, street, phone, question, answer, status) values (1, 'Test', 'Test', 'passpass', 'test@test.com', 1, 'Test', 'Test', 'Test', 'Test', 'ACTIVATED');

insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (1, 'Hotel Putnik', 1, 1, 'Ilije Ognjanovica 24', 'Hotel putnik se nalazi u centru Novog Sada.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (2, 'Hotel Master', 1, 1, 'Brace Popovic bb', 'Hotel se nalazi kod sajma.', 1, 1);
insert into accommodation (accommodation_id, name, accommodation_type_id, city_id, street, description, accommodation_category_id, agent_id) values (3, 'Privatni smestaj Petrovic', 1, 2, 'Svetogorska 1', 'Smestaj se nalazi u Beogradu.', 2, 2);

insert into bed_type (bed_type_id, bed_type_name) values (1, 'King size bed');
insert into bed_type (bed_type_id, bed_type_name) values (2, 'Small bed');

insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (1, 'Room 1', 1, 'Room 1 description', 1, 80, 4, 4);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (2, 'Room 2', 1, 'Room 2 description', 1, 80, 4, 4);
insert into apartment (apartment_id, name, bed_type_id, description, accommodation_id, size, number_of_guests, number_of_rooms) values (3, 'Room 3', 1, 'Room 3 description', 2, 50, 2, 2);

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

insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (1, 1, '18/06/2018', '30/06/2018', 1500, 'ACTIVE');
insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (2, 1, '01/07/2018', '07/07/2018', 1500, 'ACTIVE');
insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (3, 1, '08/07/2018', '16/07/2018', 1500, 'ACTIVE');

insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (4, 2, '18/06/2018', '30/06/2018', 1500, 'ACTIVE');
insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (5, 2, '01/07/2018', '07/07/2018', 1500, 'ACTIVE');
insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (6, 2, '08/07/2018', '16/07/2018', 1500, 'ACTIVE');

insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (7, 3, '18/06/2018', '30/06/2018', 1500, 'ACTIVE');
insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (8, 3, '01/07/2018', '07/07/2018', 1500, 'ACTIVE');
insert into price_plan(price_plan_id, apartment_id, start_date, end_date, price, status) values (9, 3, '08/07/2018', '16/07/2018', 1500, 'ACTIVE');

insert into reservation(reservation_id, user_id, apartment_id, start_date, end_date, price, status) values (1, 1, 1, '10/06/2018', '18/06/2018', 1500, 'RESERVATION');

insert into image(image_id, url, accommodation_id) values (1, 'https://t-ec.bstatic.com/images/hotel/max1280x900/223/22392909.jpg', 1);
insert into image(image_id, url, accommodation_id) values (2, 'http://www.mojnovisad.com/files/_thumb/600x400/location/1/4/0/140/hotel-putnik-3.jpg', 1);
insert into image(image_id, url, accommodation_id) values (3, 'http://www.hotelputnik.rs/images/temp_slide_4.jpg', 1);

insert into image(image_id, url, accommodation_id) values (4, 'https://s-ec.bstatic.com/images/hotel/max1280x900/412/41215572.jpg', 2);
insert into image(image_id, url, accommodation_id) values (5, 'http://a-hotel-master.com/dost/media/3875.jpg', 2);
insert into image(image_id, url, accommodation_id) values (6, 'https://www.google.rs/imgres?imgurl=https%3A%2F%2Ft-ec.bstatic.com%2Fimages%2Fhotel%2Fmax1280x900%2F255%2F25544234.jpg&imgrefurl=https%3A%2F%2Fwww.booking.com%2Fhotel%2Fbr%2Fmaster-governador-valadares.html&docid=ik-_fjBHvxe7YM&tbnid=ZafkN5OZ82bgbM%3A&vet=10ahUKEwiy6tn0terbAhXKmLQKHV2TCv4QMwg-KA0wDQ..i&w=1200&h=900&bih=927&biw=958&q=hotel%20master&ved=0ahUKEwiy6tn0terbAhXKmLQKHV2TCv4QMwg-KA0wDQ&iact=mrc&uact=8', 2);

insert into image(image_id, url, accommodation_id) values (7, 'http://www.sokobanja.travel/images/stanodavci/99/thumb/970361069872.jpg', 3);
insert into image(image_id, url, accommodation_id) values (8, 'http://www.amostravel.rs/wp-content/uploads/2013/06/Halkidiki-Pefkohori-vila-jenny-1-3-S.jpg', 3);
insert into image(image_id, url, accommodation_id) values (9, 'http://media.equityapartments.com/images/c_crop,x_0,y_0,w_1920,h_1080/c_fill,w_1920,h_1080/q_80/4206-28/the-kelvin-apartments-exterior.jpg', 3);

insert into image(image_id, url, apartment_id) values (10, 'http://www.oceanologyinternational.com/RXUK/RXUK_OceanologyInternational/Accommodation.jpg?v=636446260283058304', 1);
insert into image(image_id, url, apartment_id) values (11, 'http://erasmus-porto.eu/wp-content/uploads/2015/11/Accommodation.jpg', 1);
insert into image(image_id, url, apartment_id) values (12, 'http://cdn3.discovertuscany.com/img/properties/oltrarno-apartment/florence-apartment-bedroom.jpg?auto=compress,enhance,format&w=460', 1);

insert into image(image_id, url, apartment_id) values (13, 'http://gokarna.com/uploaded/slideshow/edit_SRS3023.jpg', 2);
insert into image(image_id, url, apartment_id) values (14, 'http://www.yourdictionary.com/images/definitions/lg/10096.accommodation.jpg', 2);
insert into image(image_id, url, apartment_id) values (15, 'http://www.oceanologyinternational.com/RXUK/RXUK_OceanologyInternational/Accommodation.jpg?v=636446260283058304', 2);

insert into image(image_id, url, apartment_id) values (16, 'http://portoplatanias.gr/wp-content/uploads/2013/07/Accommodation-1.jpg', 3);
insert into image(image_id, url, apartment_id) values (17, 'https://conferencecentre.wellcomegenomecampus.org/wp-content/uploads/2016/08/Accommodation-hero-960x638.jpg', 3);
insert into image(image_id, url, apartment_id) values (18, 'http://www.bloubergstrandselfcatering.co.za/images/slider/img1.jpg', 3);
