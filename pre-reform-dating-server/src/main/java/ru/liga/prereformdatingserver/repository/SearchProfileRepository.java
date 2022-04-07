package ru.liga.prereformdatingserver.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;
import ru.liga.prereformdatingserver.service.search.SearchProfileRowMapper;

import java.util.List;

public interface SearchProfileRepository extends Repository<SearchProfileProjection, Long> {

    @Query(value = "SELECT DISTINCT " +
            "DESIRED_PROFILE.CHAT_ID, " +
            "DESIRED_PROFILE.NAME, " +
            "DESIRED_PROFILE.SEX, " +
            "DESIRED_PROFILE.AVATAR, " +
            "CASE " +
            "WHEN EXISTS (SELECT * FROM DATING.FAVOURITES " +
            "             WHERE FROM_CHAT_ID = DESIRED_PROFILE.CHAT_ID " +
            "             AND TO_CHAT_ID = :chatId) " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END AS POTENTIAL_MATCH " +
            "FROM DATING.USER_PROFILE AS DESIRED_PROFILE " +
            "JOIN DATING.PREFERENCES AS DESIRED_PROFILE_PREFERENCES USING(CHAT_ID) " +
            "WHERE DESIRED_PROFILE.SEX IN (SELECT SEX FROM DATING.PREFERENCES AS USER_PREF " +
            "                               WHERE USER_PREF.CHAT_ID = :chatId) " +
            "AND DESIRED_PROFILE.CHAT_ID != :chatId " +
            "AND DESIRED_PROFILE_PREFERENCES.SEX = CAST(:sex AS DATING.SEX) " +
            "AND NOT EXISTS " +
            "               (SELECT * FROM DATING.FAVOURITES AS USER_FAV " +
            "                WHERE USER_FAV.FROM_CHAT_ID = :chatId " +
            "                AND USER_FAV.TO_CHAT_ID = DESIRED_PROFILE.CHAT_ID)",
            rowMapperClass = SearchProfileRowMapper.class)
    List<SearchProfileProjection> searchProfiles(@Param("chatId") Long chatId,
                                                 @Param("sex") String sex);
}
