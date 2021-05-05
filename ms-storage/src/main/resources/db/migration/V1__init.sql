CREATE SCHEMA storage_schema;

CREATE TABLE storage_schema.product_items
(
    id bigserial NOT NULL,
    product_id bigint NOT NULL,
    storage_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

CREATE TABLE storage_schema.storages
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    address character varying NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (id)
);

ALTER TABLE market2.product_items
    ADD CONSTRAINT fk_storages_id FOREIGN KEY (storage_id)
        REFERENCES market2.storages (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

