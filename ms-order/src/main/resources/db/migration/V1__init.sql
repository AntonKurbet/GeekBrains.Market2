CREATE TABLE orders
(
    id bigserial NOT NULL,
    total_price numeric(9, 2) NOT NULL,
    customer_id bigint NOT NULL,
    address character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE order_items
(
    id bigint NOT NULL,
    product_id bigint NOT NULL,
    order_id bigint NOT NULL references orders(id),
    quantity integer NOT NULL,
    price_per_product numeric(9,2) NOT NULL,
    price numeric(9,2) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE carts
(
    id UUID primary key,
    user_id bigint,
    price numeric(9,2)
);

CREATE TABLE cart_items
(
    id bigserial primary key,
    cart_id UUID references carts (id),
    product_id bigint,
    title varchar(255),
    quantity int,
    price_per_product numeric(9,2),
    price numeric(9,2),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone
);

-- ALTER TABLE order_items
--     ADD CONSTRAINT fk_orders_id FOREIGN KEY (order_id)
--         REFERENCES market2.orders (id)
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
--         NOT VALID;

