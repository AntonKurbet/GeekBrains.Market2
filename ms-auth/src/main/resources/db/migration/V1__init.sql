CREATE TABLE users
(
    id bigserial NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    role_id bigint NOT NULL,
    email character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id bigserial NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT fk_roles_id FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users (login, password, role_id, email)
    VALUES ('admin', '$2a$10$ZYQ1CeU3GwFb.nFAVpHsO.H56QBJJjMcOcUtuJ0FsQGpVHg6dLGs6', 1, 'dba@admin.org')