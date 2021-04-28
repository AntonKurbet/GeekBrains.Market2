CREATE SCHEMA core_schema;

CREATE TABLE core_schema.users
(
    id bigserial NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    role_id bigint NOT NULL,
    email character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE core_schema.roles
(
    id bigserial NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE core_schema.users
    ADD CONSTRAINT fk_roles_id FOREIGN KEY (role_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;