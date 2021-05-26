CREATE TABLE products
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    price numeric(9, 2) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE products_categories (
    product_id bigint REFERENCES products (id),
    category_id bigint REFERENCES categories (id));


INSERT INTO categories (title, created_at) VALUES ('food', now());

INSERT INTO products (title, price, created_at)
    VALUES ('cheese', 102.99, now());

INSERT INTO products_categories (product_id, category_id) VALUES (1,1);