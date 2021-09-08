--drop table role; drop table person; drop table users;

create table role(
    id int NOT NULL,
    name varchar(255),
    PRIMARY KEY(id)

);

create table person(
    id int NOT NULL,
    email varchar(255),
    name varchar(255),
    phone_number varchar(255),
    surname varchar(255),
    PRIMARY KEY(id)
);

create table users(
    id int NOT NULL,
    email varchar(255),
    password varchar(255),
    person int NOT NULL,
    role int NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(person) REFERENCES person(id),
    FOREIGN KEY(role) REFERENCES role(id)
);

create table address(
    id int NOT NULL,
    city varchar(255),
    country varchar(255),
    province varchar(255),
    street varchar(255),
    user_id int NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

create table type(
    id int NOT NULL,
    name varchar(255),
    PRIMARY KEY(id)
);

create table announcement(
    id int NOT NULL,
    description varchar(500),
    title varchar(255),
    address int NOT NULL,
    type int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(address) REFERENCES address(id),
    FOREIGN KEY(type) REFERENCES type(id),
    FOREIGN KEY(user_id) REFERENCES users(id)
);



insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'CLIENT');

insert into person (id, email, name, phone_number, surname) values (1, 'jan_kowalski@op.pl', 'Jan', '746395637', 'Kowalski');
insert into person (id, email, name, phone_number, surname) values (2, 'piotr_drewno@op.pl', 'Piotr', '648354036', 'Drewno');

insert into users (id, email, password, person, role) values (1, 'jan_kowalski@op.pl', '$2a$10$OBBWmFGHGfL48U8lb5XeMec5Q65H4kDUoeb9hr.zUnjZ0IqwOKTMy', 1, 1);
insert into users (id, email, password, person, role) values (2, 'piotr_drewno@op.pl', '$2a$10$OBBWmFGHGfL48U8lb5XeMec5Q65H4kDUoeb9hr.zUnjZ0IqwOKTMy', 2, 2);

insert into address (id, city, country, province, street, user_id) values (1, 'Kraków', 'Poland', 'Małopolska', 'Krakowska 7', 2);

insert into type (id, name) values (1, 'Sale');
insert into type (id, name) values (2, 'Rent');
insert into type (id, name) values (3, 'Give away for free');

insert into announcement (id, description, title, address, type, user_id) values (1, 'Cannondale 194 Mountain bike', 'Bike for sell', 1, 1, 2);