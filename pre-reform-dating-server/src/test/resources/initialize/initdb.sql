CREATE SCHEMA DATING;

CREATE TYPE dating.sex AS ENUM ('Сударъ', 'Сударыня');

CREATE CAST (varchar AS dating.sex) WITH INOUT AS IMPLICIT;

CREATE TABLE dating.user_profile
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id     BIGINT       NOT NULL,
    name        VARCHAR(127) NOT NULL,
    sex         dating.sex   NOT NULL,
    description TEXT         NOT NULL,
    avatar      VARCHAR(255),
    CONSTRAINT pk_user_profile PRIMARY KEY (id),
    CONSTRAINT user_profile_chat_id_unique UNIQUE (chat_id)
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

-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (100, 'U_100', 'U_100 like_to(500,700) like_from(500) mathces(500)', 'Сударъ', '1.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (200, 'U_200', 'U_200 like_to(600) like_from(700) mathces(-)', 'Сударъ', '2.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (300, 'U_300', 'U_300 like_to(700) like_from(600) mathces(-)', 'Сударъ', '3.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (400, 'U_400', 'U_4_INFO', 'Сударъ', '4.jpg');
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (500, 'U_500', 'U_500 like_to(100) like_from(100) mathces(100)', 'Сударъ', '5.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (600, 'U_600', 'U_600 like_to(300) like_from(200) mathces(-)', 'Сударъ', '6.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (700, 'U_700', 'U_700 like_to(200) like_from(300) mathces(-)', 'Сударъ', '7.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (800, 'U_800', 'U_800 FIND_ALL', 'Сударыня', '8.jpg');
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
VALUES (700, 200);

--FIND ALL
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (1000, 'U_1000', 'U_1000_INFO', 'Сударъ', '1.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (2000, 'U_2000', 'U_2000_INFO', 'Сударъ', '2.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (3000, 'U_3000', 'U_3000_INFO', 'Сударъ', '3.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (4000, 'U_4000', 'U_4000_INFO', 'Сударъ', '4.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (5000, 'U_5000', 'U_5000_INFO', 'Сударъ', '5.jpg');
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (6000, 'U_6000', 'U_6000_INFO', 'Сударыня', '6.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (7000, 'U_7000', 'U_7000_INFO', 'Сударыня', '7.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (8000, 'U_8000', 'U_8000_INFO', 'Сударыня', '8.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (9000, 'U_9000', 'U_9000_INFO', 'Сударыня', '9.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (10000, 'U_10000', 'U_10000_INFO', 'Сударыня', '10.jpg');
-------------------------------------------------------------------------------------
INSERT INTO dating.preferences (chat_id, sex)
VALUES (1000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (1000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (2000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (2000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (3000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (3000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (4000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (4000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (5000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (5000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (6000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (6000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (7000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (7000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (8000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (8000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (9000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (9000, 'Сударыня');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (10000, 'Сударъ');
INSERT INTO dating.preferences (chat_id, sex)
VALUES (10000, 'Сударыня');
-------------------------------------------------------------------------------------