CREATE DATABASE hotel_development CHARACTER SET utf8;
CREATE USER 'hotel_user'@'localhost' IDENTIFIED BY 'hotel_pass';
GRANT ALL PRIVILEGES ON hotel_development.* TO 'hotel_user'@'localhost';

DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS RoomType;
DROP TABLE IF EXISTS CreditCard;
DROP TABLE IF EXISTS StreetLocation;
DROP TABLE IF EXISTS PostalLocation;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (eid INTEGER, name VARCHAR(255), password VARCHAR(255), PRIMARY KEY (eid));

CREATE TABLE Customer (name VARCHAR(255), phone_number VARCHAR(255), password VARCHAR(255), PRIMARY KEY (name, phone_number));

CREATE TABLE PostalLocation(postal_code VARCHAR(255), city VARCHAR(255), province VARCHAR(255), PRIMARY KEY (postal_code));

CREATE TABLE StreetLocation( address_no VARCHAR(255), street VARCHAR(255), postal_code VARCHAR(255), PRIMARY KEY(address_no, street, postal_code), FOREIGN KEY(postal_code) REFERENCES PostalLocation(postal_code));

CREATE TABLE CreditCard (cc_number VARCHAR(255), expiry_date VARCHAR(255), address_no VARCHAR(255) NOT NULL, street VARCHAR(255) NOT NULL, postal_code VARCHAR(255) NOT NULL, PRIMARY KEY (cc_number), FOREIGN KEY (address_no, street, postal_code) REFERENCES StreetLocation(address_no, street, postal_code));

CREATE TABLE RoomType (type VARCHAR(255), securityDeposit INTEGER, daily_rate INTEGER, PRIMARY KEY(type));

CREATE TABLE Room (r_number INTEGER, type VARCHAR(255) NOT NULL, address_no VARCHAR(255), street VARCHAR(255), postal_code VARCHAR(255), PRIMARY KEY(r_number, address_no, street, postal_code), FOREIGN KEY(address_no, street, postal_code) REFERENCES StreetLocation(address_no, street, postal_code), FOREIGN KEY(type) REFERENCES RoomType(type));

CREATE TABLE Reservation (conf_no INTEGER, checkin_date    DATE, checkout_date DATE, comments VARCHAR(255), address_no VARCHAR(255) NOT NULL, street VARCHAR(255) NOT NULL, postal_code VARCHAR(255) NOT NULL, r_number INTEGER NOT NULL, eid INTEGER, name VARCHAR(255) NOT NULL, phone_number VARCHAR(255) NOT NULL, cc_number VARCHAR(255) NOT NULL, PRIMARY KEY (conf_no), FOREIGN KEY (r_number, address_no, street, postal_code) REFERENCES Room(r_number, address_no, street, postal_code), FOREIGN KEY (eid) REFERENCES Employee(eid), FOREIGN KEY (name, phone_number) REFERENCES Customer(name, phone_number), FOREIGN KEY (cc_number) REFERENCES CreditCard(cc_number));
