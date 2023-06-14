drop table if EXISTS USERS;
drop table if EXISTS product;

create TABLE users (
  idx                 INT               PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id             varchar(255)      UNIQUE NOT NULL,
  password            varchar(255)      NOT NULL,
  nickname            varchar(255)      UNIQUE,
  email               varchar(255)      NOT NULL,
  user_phone_number   varchar(255)      NOT NULL,
  created_at          datetime          DEFAULT CURRENT_TIMESTAMP,
  created_by          varchar(255)      NOT NULL,
  updated_at          datetime,
  updated_by          varchar(255),
  deleted_at          datetime
);

create TABLE product (
    idx              INT              PRIMARY KEY NOT NULL AUTO_INCREMENT,
    company_idx      INT              NOT NULL,
    product_name     varchar(200)     NOT NULL,
    product_price    INT              NOT NULL,
    product_maker    varchar(200)     NOT NULL,
    description      varchar(2000)    NOT NULL,
    product_img_url  varchar(2000)    NULL,
    kcal             INT              NOT NULL,
    protein          INT              NOT NULL,
    carbohydrate     INT              NOT NULL,
    fat              INT              NOT NULL,
    created_at       datetime         DEFAULT CURRENT_TIMESTAMP,
    created_by       varchar(16)      NOT NULL,
    updated_at       datetime         NULL,
    updated_by       varchar(16)      NULL,
    deleted_at       datetime         NULL
);

