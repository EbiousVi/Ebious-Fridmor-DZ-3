package ru.liga.prereformdatingserver.service.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;

import java.util.List;

public interface SearchProfileRepository extends Repository<SearchProfileProjection, Long> {

    @Query(value = "SELECT DISTINCT " +
            "SEARCHED_PROFILE.CHAT_ID, " +
            "SEARCHED_PROFILE.NAME, " +
            "SEARCHED_PROFILE.SEX, " +
            "SEARCHED_PROFILE.AVATAR, " +
            "CASE " +
            "WHEN EXISTS (SELECT * FROM DATING.FAVOURITES WHERE FROM_CHAT_ID = SEARCHED_PROFILE.CHAT_ID AND TO_CHAT_ID = :chatId) THEN TRUE " +
            "ELSE FALSE " +
            "END AS POTENTIAL_MATCH " +
            "FROM DATING.USER_PROFILE AS SEARCHED_PROFILE " +
            "JOIN DATING.PREFERENCES AS SEARCHED_PREF USING(CHAT_ID) " +
            "WHERE SEARCHED_PROFILE.SEX IN (SELECT SEX FROM DATING.PREFERENCES AS USER_PREF WHERE USER_PREF.CHAT_ID = :chatId) " +
            "AND SEARCHED_PROFILE.CHAT_ID != :chatId " +
            "AND SEARCHED_PREF.SEX = (SELECT SEX FROM DATING.USER_PROFILE AS USER_PROF WHERE USER_PROF.CHAT_ID = :chatId) " +
            "AND NOT EXISTS " +
            "(SELECT * FROM DATING.FAVOURITES AS USER_FAV " +
            "WHERE USER_FAV.FROM_CHAT_ID = :chatId " +
            "AND USER_FAV.TO_CHAT_ID = SEARCHED_PROFILE.CHAT_ID)", rowMapperClass = SearchProfileRowMapper.class)
    List<SearchProfileProjection> searchProfiles(@Param("chatId") Long chatId);
}
