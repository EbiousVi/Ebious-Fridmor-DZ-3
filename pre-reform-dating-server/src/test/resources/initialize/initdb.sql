CREATE SCHEMA IF NOT EXISTS DATING;

CREATE TYPE dating.sex AS ENUM ('Сударъ', 'Сударыня');

CREATE CAST (VARCHAR AS dating.sex) WITH INOUT AS IMPLICIT;

CREATE TABLE dating.user_profile
(
    chat_id     BIGINT       NOT NULL,
    name        VARCHAR(127) NOT NULL,
    password    VARCHAR(255) NOT NULL,
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

CREATE TABLE dating.credentials
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id       BIGINT REFERENCES dating.user_profile (chat_id) ON DELETE CASCADE ON UPDATE CASCADE,
    refresh_token BIGINT REFERENCES dating.user_profile (chat_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT PK_credentials PRIMARY KEY (id)
);
--MALE
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (100, 'password', 'U_100', 'U_100_DESCRIPTION', 'Сударъ', '1.jpg');
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (200, 'password', 'U_200', 'U_200_DESCRIPTION', 'Сударъ', '2.jpg');
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (300, 'password', 'U_300', 'U_300_DESCRIPTION', 'Сударъ', '3.jpg');
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (400, 'password', 'U_400', 'U_400_DESCRIPTION', 'Сударъ', '4.jpg');
--FEMALE
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (500, 'password', 'U_500', 'U_500_DESCRIPTION', 'Сударыня', '5.jpg');
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (600, 'password', 'U_600', 'U_600_DESCRIPTION', 'Сударыня', '6.jpg');
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (700, 'password', 'U_700', 'U_700_DESCRIPTION', 'Сударыня', '7.jpg');
INSERT INTO dating.user_profile (chat_id, password, name, description, sex, avatar)
VALUES (800, 'password', 'U_800', 'U_800_DESCRIPTION', 'Сударыня', '8.jpg');
-------------------------------------------------------------------------------------
INSERT INTO dating.preferences (chat_id, sex)
VALUES (100, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (200, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (300, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (400, 'Сударыня');
-------------------------------------------------------------------------------------
INSERT INTO dating.preferences (chat_id, sex)
VALUES (500, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (600, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (700, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (800, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (800, 'Сударыня');
--bi
-------------------------------------------------------------------------------------
--MATCHES
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (100, 500);
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (500, 100);
--MALE LIKES
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (100, 700);
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (200, 600);
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (300, 700);
--FEMALE LIKES
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (600, 300);
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (600, 100);
INSERT INTO dating.favourites (from_chat_id, to_chat_id)
VALUES (700, 200);