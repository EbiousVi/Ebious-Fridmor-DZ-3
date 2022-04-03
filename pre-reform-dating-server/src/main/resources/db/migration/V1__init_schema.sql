CREATE TYPE dating.sex AS ENUM ('Сударъ', 'Сударыня');

CREATE CAST (varchar AS dating.sex) WITH INOUT AS IMPLICIT;

CREATE TABLE dating.user_profile
(
    chat_id     BIGINT       NOT NULL,
    name        VARCHAR(127) NOT NULL,
    sex         dating.sex   NOT NULL,
    description TEXT         NOT NULL,
    avatar      VARCHAR(255),
    CONSTRAINT pk_user_profile PRIMARY KEY (chat_id)
);

CREATE TABLE dating.preferences
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id BIGINT     NOT NULL REFERENCES dating.user_profile (chat_id),
    sex     dating.sex NOT NULL,
    CONSTRAINT pk_preferences PRIMARY KEY (id),
    CONSTRAINT preferences_chat_id_sex_unique UNIQUE (chat_id, sex)
);

CREATE TABLE dating.favourites
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    from_chat_id BIGINT REFERENCES dating.user_profile (chat_id),
    to_chat_id   BIGINT REFERENCES dating.user_profile (chat_id),
    CONSTRAINT pk_favourites PRIMARY KEY (id),
    CONSTRAINT from_chat_id_to_chat_id_unique UNIQUE (from_chat_id, to_chat_id)
);