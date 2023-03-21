DROP TABLE if EXISTS product;

CREATE TABLE product (
    idx              numeric(10)      PRIMARY KEY NOT NULL AUTO_INCREMENT,
    company_idx      numeric(10)      NOT NULL,
    product_name     varchar(30)      NOT NULL,
    product_price    numeric(10)      NOT NULL,
    product_maker    varchar(20)      NOT NULL,
    description      text             NOT NULL,
    product_img_url  varchar(200)     NOT NULL,
    protein          numeric(5)       NOT NULL,
    carbohydrate     numeric(5)       NOT NULL,
    fat              numeric(5)       NOT NULL,
    cal              numeric(10)      NOT NULL,
    created_at       datetime         DEFAULT CURRENT_DATE,
    created_by       varchar(16)      NOT NULL,
    updated_at       datetime         NULL,
    updated_by       varchar(16)      NULL,
    deleted_at       datetime         NULL,
    deleted_by       varchar(16)      NULL
);