CREATE TABLE IF NOT EXISTS users.users
(
    user_name character varying COLLATE pg_catalog."default" NOT NULL,
    count integer,
    CONSTRAINT users_pk PRIMARY KEY (user_name)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS users.users
    OWNER to postgres;