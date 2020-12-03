CREATE DATABASE IF NOT EXISTS rewardpoint;

ALTER DATABASE rewardpoint
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON rewardpoint.* TO pc@localhost IDENTIFIED BY 'pc';

USE rewardpoint;

CREATE TABLE IF NOT EXISTS owners (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(20),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS rewardpoints (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  owner_id INT(4) UNSIGNED NOT NULL,
  recognizer_id INT(4) UNSIGNED,
  points INTEGER DEFAULT 5,
  rewardpoint_date DATE,
  description VARCHAR(255),
  FOREIGN KEY (owner_id) REFERENCES owners(id)
) engine=InnoDB;
