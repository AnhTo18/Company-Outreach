CREATE TABLE IF NOT EXISTS owners2 (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(30),
  username VARCHAR(30),
  password VARCHAR(255),
  INDEX(last_name)
) engine=InnoDB;
