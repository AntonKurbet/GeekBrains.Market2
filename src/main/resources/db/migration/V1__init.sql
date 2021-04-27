CREATE TABLE market2.users
(
    id bigserial NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE market2.products
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    price numeric(9, 2) NOT NULL,
    category_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE market2.categories
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

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

CREATE TABLE market2.product_items
(
    id bigserial NOT NULL,
    product_id bigint NOT NULL,
    storage_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE market2.storages
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    address character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

ALTER TABLE market2.products
    ADD CONSTRAINT fk_categories_id FOREIGN KEY (category_id)
        REFERENCES market2.categories (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE market2.product_items
    ADD CONSTRAINT fk_products_id FOREIGN KEY (product_id)
        REFERENCES market2.products (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE market2.product_items
    ADD CONSTRAINT fk_storages_id FOREIGN KEY (storage_id)
        REFERENCES market2.storages (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE market2.order_items
    ADD CONSTRAINT fk_orders_id FOREIGN KEY (order_id)
        REFERENCES market2.orders (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE market2.order_items
    ADD CONSTRAINT fk_products_id FOREIGN KEY (product_id)
        REFERENCES market2.products (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE market2.orders
    ADD CONSTRAINT fk_users_id FOREIGN KEY (customer_id)
        REFERENCES market2.users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;