CREATE SCHEMA products_schema;

CREATE TABLE products_schema.products
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

CREATE TABLE products_schema.categories
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

ALTER TABLE products_schema.products
    ADD CONSTRAINT fk_categories_id FOREIGN KEY (category_id)
        REFERENCES products_schema.categories (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;
