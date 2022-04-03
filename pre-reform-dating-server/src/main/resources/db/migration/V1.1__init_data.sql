--MALE
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (100, 'U_100', 'U_100_DESCRIPTION', 'Сударъ', '1.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (200, 'U_200', 'U_200_DESCRIPTION', 'Сударъ', '2.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (300, 'U_300', 'U_300_DESCRIPTION', 'Сударъ', '3.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (400, 'U_400', 'U_400_DESCRIPTION', 'Сударъ', '4.jpg');
--FEMALE
-------------------------------------------------------------------------------------
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (500, 'U_500', 'U_500_DESCRIPTION', 'Сударыня', '5.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (600, 'U_600', 'U_600_DESCRIPTION', 'Сударыня', '6.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (700, 'U_700', 'U_700_DESCRIPTION', 'Сударыня', '7.jpg');
INSERT INTO dating.user_profile (chat_id, name, description, sex, avatar)
VALUES (800, 'U_800', 'U_800_DESCRIPTION', 'Сударыня', '8.jpg');
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