/*
CREATE SCHEMA order_schema;

CREATE TABLE market2.orders
(
    id bigserial NOT NULL,
    orderitem_id bigint NOT NULL,
    total_price numeric(9, 2) NOT NULL,
    customer_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE market2.order_items
(
    id bigint NOT NULL,
    product_id bigint NOT NULL,
    order_id bigint NOT NULL,
    quantity integer NOT NULL,
    price_per_product numeric(9,2) NOT NULL,
    price numeric(9,2) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

ALTER TABLE market2.order_items
    ADD CONSTRAINT fk_orders_id FOREIGN KEY (order_id)
        REFERENCES market2.orders (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;
*/
