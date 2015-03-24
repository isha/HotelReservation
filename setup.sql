DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS RoomType;
DROP TABLE IF EXISTS CreditCard;
DROP TABLE IF EXISTS StreetLocation;
DROP TABLE IF EXISTS PostalLocation;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (eid INTEGER AUTO_INCREMENT,
  name VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY (eid));

CREATE TABLE Customer (name VARCHAR(255),
  phone_number VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY (name, phone_number));

CREATE TABLE PostalLocation(postal_code VARCHAR(255),
  city VARCHAR(255),
  province VARCHAR(255),
  PRIMARY KEY (postal_code));

CREATE TABLE StreetLocation(address_no VARCHAR(255),
  street VARCHAR(255),
  postal_code VARCHAR(255),
  PRIMARY KEY(address_no, street, postal_code),
  FOREIGN KEY(postal_code) REFERENCES PostalLocation(postal_code) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE CreditCard (cc_number VARCHAR(255),
  expiry_date VARCHAR(255),
  address_no VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL,
  postal_code VARCHAR(255) NOT NULL,
  PRIMARY KEY (cc_number),
  FOREIGN KEY (address_no, street, postal_code) REFERENCES StreetLocation(address_no, street, postal_code) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE RoomType (type VARCHAR(255),
  securityDeposit INTEGER,
  daily_rate INTEGER, PRIMARY KEY(type));

CREATE TABLE Room (r_number INTEGER,
  type VARCHAR(255) NOT NULL,
  address_no VARCHAR(255),
  street VARCHAR(255),
  postal_code VARCHAR(255),
  PRIMARY KEY(r_number, address_no, street, postal_code),
  FOREIGN KEY(address_no, street, postal_code) REFERENCES StreetLocation(address_no, street, postal_code) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY(type) REFERENCES RoomType(type) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE Reservation (conf_no INTEGER AUTO_INCREMENT,
  checkin_date DATE,
  checkout_date DATE,
  comments VARCHAR(255),
  address_no VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL,
  postal_code VARCHAR(255) NOT NULL,
  r_number INTEGER NOT NULL,
  eid INTEGER,
  name VARCHAR(255) NOT NULL,
  phone_number VARCHAR(255) NOT NULL,
  cc_number VARCHAR(255) NOT NULL,
  PRIMARY KEY (conf_no),
  FOREIGN KEY (r_number, address_no, street, postal_code) REFERENCES Room(r_number, address_no, street, postal_code) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (eid) REFERENCES Employee(eid) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (name, phone_number) REFERENCES Customer(name, phone_number) ON UPDATE CASCADE,
  FOREIGN KEY (cc_number) REFERENCES CreditCard(cc_number) ON UPDATE CASCADE);

INSERT INTO RoomType VALUES ('queen', 150, 200);
INSERT INTO RoomType VALUES ('single', 90, 120);
INSERT INTO RoomType VALUES ('king', 170, 250);
INSERT INTO RoomType VALUES ('suite', 200, 500);
INSERT INTO RoomType VALUES ('presidential', 1000, 700);

INSERT INTO Employee VALUES (1, 'Denise', 'password1');
INSERT INTO Employee VALUES (2, 'Mackenzie', 'password2Mackenzie');
INSERT INTO Employee VALUES (3, 'Isha', 'Ishaspassword');
INSERT INTO Employee VALUES (4, 'Jonathan', 'passwordofJonathan');
INSERT INTO Employee VALUES (5, 'Bob', 'bob_the_password');

INSERT INTO Customer VALUES ('Mackenzie Judd', '6041239999', 'mackenzie_9999_pw');
INSERT INTO Customer VALUES ('Allie Wilson', '7771565678', 'wilson_5678_pw');
INSERT INTO Customer VALUES ('Scott Brie', '7789970687', 'brie_0687_pw');
INSERT INTO Customer VALUES ('Mal Mallard', '4069089820', 'mallad_9820_pw');
INSERT INTO Customer VALUES ('Jimmy Joy', '5139087893', 'joy_7893_pw');

INSERT INTO PostalLocation VALUES ('V6G 2L2', 'Calgary', 'Alberta');
INSERT INTO PostalLocation VALUES ('H8J 4L4', 'Vancouver', 'British Columbia');
INSERT INTO PostalLocation VALUES ('V5K 4A1', 'Coquitlam', 'British Columbia');
INSERT INTO PostalLocation VALUES ('L0L 4O4', 'Winnipeg', 'Manitoba');
INSERT INTO PostalLocation VALUES ('V0N 3A1', 'Toronto', 'Ontario');

INSERT INTO StreetLocation VALUES (1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO StreetLocation VALUES (1245, 'W. 49th', 'H8J 4L4');
INSERT INTO StreetLocation VALUES (1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO StreetLocation VALUES (6457, 'Apple Road', 'L0L 4O4');
INSERT INTO StreetLocation VALUES (1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO StreetLocation VALUES (1194, 'Rossland', 'V5K 4A1');
INSERT INTO StreetLocation VALUES (2241, 'Main', 'L0L 4O4');
INSERT INTO StreetLocation VALUES (6455, 'Orange Road', 'L0L 4O4');

INSERT INTO Room VALUES (100, 'single', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (101, 'single', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (102, 'single', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (200, 'queen', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (202, 'queen', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (301, 'king', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (303, 'king', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (400, 'suite', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (405, 'suite', 1260, 'Bidwell Street', 'V6G 2L2');
INSERT INTO Room VALUES (500, 'presidential', 1260, 'Bidwell Street', 'V6G 2L2');

INSERT INTO Room VALUES (100, 'single', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (101, 'single', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (102, 'single', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (200, 'queen', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (202, 'queen', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (300, 'king', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (303, 'king', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (400, 'suite', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (405, 'suite', 1245, 'W. 49th', 'H8J 4L4');
INSERT INTO Room VALUES (500, 'presidential', 1245, 'W. 49th', 'H8J 4L4');

INSERT INTO Room VALUES (203, 'single', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (204, 'single', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (205, 'single', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (320, 'queen', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (322, 'queen', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (424, 'king', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (482, 'king', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (522, 'suite', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (512, 'suite', 1234, 'Shaw Avenue', 'V5K 4A1');
INSERT INTO Room VALUES (700, 'presidential', 1234, 'Shaw Avenue', 'V5K 4A1');

INSERT INTO Room VALUES (120, 'single', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (130, 'single', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (140, 'single', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (250, 'queen', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (270, 'queen', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (400, 'king', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (420, 'king', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (540, 'suite', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (520, 'suite', 6457, 'Apple Road', 'L0L 4O4');
INSERT INTO Room VALUES (892, 'presidential', 6457, 'Apple Road', 'L0L 4O4');

INSERT INTO Room VALUES (124, 'single', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (190, 'single', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (191, 'single', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (301, 'queen', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (322, 'queen', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (530, 'king', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (532, 'king', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (629, 'suite', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (635, 'suite', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO Room VALUES (739, 'presidential', 1992, 'Stalashen Drive', 'V0N 3A1');

INSERT INTO CreditCard VALUES ('378282246310005', '2017-03-01', 1194, 'Rossland', 'V5K 4A1');
INSERT INTO CreditCard VALUES ('3530111333300000', '2015-10-01', 2241, 'Main', 'L0L 4O4');
INSERT INTO CreditCard VALUES ('5555555555554444', '2016-05-01', 6455, 'Orange Road', 'L0L 4O4');
INSERT INTO CreditCard VALUES ('6011111111111117', '2017-11-01', 1992, 'Stalashen Drive', 'V0N 3A1');
INSERT INTO CreditCard VALUES ('4111111111111111', '2019-02-01', 1234, 'Shaw Avenue', 'V5K 4A1');


INSERT INTO Reservation VALUES (11300, '2013-02-13', '2013-02-16', NULL,
  1234, 'Shaw Avenue', 'V5K 4A1', 204, NULL, 'Scott Brie', '7789970687', '5555555555554444');
INSERT INTO Reservation VALUES (NULL, '2013-10-30', '2013-11-01', 'allergic to nuts',
  1234, 'Shaw Avenue', 'V5K 4A1', 482, 3, 'Mal Mallard', '4069089820', '6011111111111117');
INSERT INTO Reservation VALUES (NULL, '2014-05-04', '2014-05-14', 'allergic to nuts',
  1992, 'Stalashen Drive', 'V0N 3A1', 739, 2, 'Mal Mallard', '4069089820', '6011111111111117');
INSERT INTO Reservation VALUES (NULL, '2014-06-10', '2014-06-15', 'here on business',
  6457, 'Apple Road', 'L0L 4O4', 120, 5, 'Jimmy Joy', '5139087893', '3530111333300000');
INSERT INTO Reservation VALUES (NULL, '2014-08-01', '2014-08-06', NULL,
  6457, 'Apple Road', 'L0L 4O4', 420, NULL, 'Jimmy Joy', '5139087893', '3530111333300000');
INSERT INTO Reservation VALUES (NULL, '2014-12-29', '2015-01-02', 'allergic to nuts',
  1245, 'W. 49th', 'H8J 4L4', 500, 4, 'Mal Mallard', '4069089820', '6011111111111117');
INSERT INTO Reservation VALUES (NULL, '2015-01-03', '2015-01-13', 'here on business',
  6457, 'Apple Road', 'L0L 4O4', 400, 2, 'Jimmy Joy', '5139087893', '3530111333300000');
INSERT INTO Reservation VALUES (NULL, '2015-01-07', '2015-01-14', 'here on business',
  6457, 'Apple Road', 'L0L 4O4', 140, 3, 'Jimmy Joy', '5139087893', '3530111333300000');
INSERT INTO Reservation VALUES (NULL, '2015-11-29', '2015-12-20', 'honeymoon',
  1260, 'Bidwell Street', 'V6G 2L2', 500, 1, 'Allie Wilson', '7771565678', '378282246310005');
INSERT INTO Reservation VALUES (NULL, '2015-12-23', '2016-01-02', 'Christmas and NYE celebration',
  1234, 'Shaw Avenue', 'V5K 4A1', 424, 2, 'Scott Brie', '7789970687', '5555555555554444');

