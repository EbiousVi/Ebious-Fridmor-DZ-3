CREATE SCHEMA IF NOT EXISTS DATING;

CREATE TYPE dating.sex AS ENUM ('Сударъ', 'Сударыня');

CREATE CAST (VARCHAR AS dating.sex) WITH INOUT AS IMPLICIT;

CREATE TABLE dating.user_profile
(
    chat_id     BIGINT       NOT NULL,
    password    VARCHAR(255) NOT NULL,
    name        VARCHAR(127) NOT NULL,
    sex         dating.sex   NOT NULL,
    description TEXT         NOT NULL,
    avatar      VARCHAR(255),
    CONSTRAINT PK_user_profile PRIMARY KEY (chat_id)
);

CREATE TABLE dating.preferences
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id BIGINT     NOT NULL REFERENCES dating.user_profile (chat_id) ON DELETE CASCADE ON UPDATE CASCADE,
    sex     dating.sex NOT NULL,
    CONSTRAINT PK_preferences PRIMARY KEY (id),
    CONSTRAINT UQ_preferences_chat_id_sex UNIQUE (chat_id, sex)
);

CREATE TABLE dating.favourites
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    from_chat_id BIGINT REFERENCES dating.user_profile (chat_id) ON DELETE CASCADE ON UPDATE CASCADE,
    to_chat_id   BIGINT REFERENCES dating.user_profile (chat_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT PK_favourites PRIMARY KEY (id),
    CONSTRAINT UQ_from_chat_id_to_chat_id UNIQUE (from_chat_id, to_chat_id)
);