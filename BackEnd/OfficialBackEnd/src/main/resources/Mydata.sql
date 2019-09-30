CREATE DATABASE paramount;

USE paramount;

CREATE TABLE employee(
	id INT(10) NOT NULL AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
	department VARCHAR(150) NOT NULL,
	salary DOUBLE NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO employee VALUES (1001,'Jose', 'Technology', 40000.67);
INSERT INTO employee VALUES (1002,'John', 'Human Resource', 20000.00);
INSERT INTO employee VALUES (1003,'Jane', 'Delivery', 80000.00);

CREATE TABLE IF NOT EXISTS testing (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  telephone VARCHAR(80),
  points INT(5),
  username VARCHAR(30),
  password VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;
INSERT INTO employee VALUES (1003,'Kordell','Schrock', 'Stanford Way','507-993-0709', 10.00, 'username','password');
INSERT INTO employee VALUES (1004,'Clark','R.', 'ISU','555-555-5555', 15, 'user','pass');

CREATE TABLE QRCodesTest(
	id INT(10) NOT NULL AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
	department VARCHAR(150) NOT NULL,
	salary DOUBLE NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO employee VALUES (1001,'Jose', 'Technology', 40000.67);
INSERT INTO employee VALUES (1002,'John', 'Human Resource', 20000.00);
INSERT INTO employee VALUES (1003,'Jane', 'Delivery', 80000.00);

CREATE TABLE IF NOT EXISTS QRCodes (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Pointsx1000 VARCHAR(30),
  Pointsx500 VARCHAR(30),
  Pointsx200 VARCHAR(30),
  Pointsx100 VARCHAR(30),
  Pointsx50 VARCHAR(30),
  Loser VARCHAR(30),
  Random VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;
INSERT INTO employee VALUES (1003,'Kordell','Schrock', 'Stanford Way','507-993-0709', 10.00, 'username','password');
INSERT INTO employee VALUES (1004,'Clark','R.', 'ISU','555-555-5555', 15, 'user','pass');