DROP TABLE if EXISTS USERS;

CREATE TABLE users (
  idx INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id varchar(255) UNIQUE NOT NULL,
  password varchar(255) NOT NULL,
  nickname varchar(255) UNIQUE,
  email varchar(255) NOT NULL,
  user_phone_number varchar(255) NOT NULL,
  created_at datetime DEFAULT CURRENT_TIMESTAMP,
  created_by varchar(255) NOT NULL,
  updated_at datetime,
  updated_by varchar(255),
  deleted_at datetime,
  deleted_by varchar(255)
);

