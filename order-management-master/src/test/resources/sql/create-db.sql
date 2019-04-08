CREATE TABLE payment (
                       id    BIGINT PRIMARY KEY,
                       price DECIMAL(30, 8) NOT NULL
);
CREATE TABLE product_order (
                             paymentId    BIGINT PRIMARY KEY,
                             productId    BIGINT NOT NULL,
                             orderAmount    BIGINT NOT NULL
);
create table product
(
  id BIGINT PRIMARY KEY,
  name varchar(255) NOT NULL,
  description varchar(255),
  unitPrice  DECIMAL(30, 8) NOT NULL,
  quantity BIGINT default 0 NOT NULL
);

