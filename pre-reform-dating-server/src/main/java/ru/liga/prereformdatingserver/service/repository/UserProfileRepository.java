package ru.liga.prereformdatingserver.service.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;

import java.util.List;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

    @Query("SELECT *\n" +
            "FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "(SELECT TO_CHAT_ID FROM DATING.FAVOURITES " +
            "WHERE FROM_CHAT_ID = :chatId)")
    List<UserProfile> findMyFavourites(@Param("chatId") Long chatId);

    @Query("SELECT * FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "(SELECT FROM_CHAT_ID FROM DATING.FAVOURITES " +
            "WHERE TO_CHAT_ID = :chatId)")
    List<UserProfile> findWhoseFavouriteAmI(@Param("chatId") Long chatId);

    @Query("SELECT * FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "(SELECT FROM_CHAT_ID FROM DATING.FAVOURITES " +
            "WHERE FROM_CHAT_ID IN " +
            "(SELECT FROM_CHAT_ID FROM DATING.FAVOURITES WHERE TO_CHAT_ID = :chatId))")
    List<UserProfile> findMatches(@Param("chatId") Long chatId);

    @Query("SELECT DISTINCT " +
            "   SEARCHED_PROFILE.CHAT_ID, " +
            "   SEARCHED_PROFILE.NAME, " +
            "   SEARCHED_PROFILE.SEX, " +
            "   SEARCHED_PROFILE.DESCRIPTION, " +
            "   SEARCHED_PROFILE.AVATAR FROM DATING.USER_PROFILE AS SEARCHED_PROFILE " +
            "WHERE SEARCHED_PROFILE.SEX IN " +
            "                              (SELECT SEX FROM DATING.PREFERENCES AS USER_PREF " +
            "                               WHERE USER_PREF.CHAT_ID = :chatId) " +
            "AND SEARCHED_PROFILE.CHAT_ID != :chatId " +
            "AND EXISTS " +
            "       (SELECT * FROM DATING.PREFERENCES AS SEARCHED_PROFILE_PREF " +
            "        WHERE SEARCHED_PROFILE_PREF.CHAT_ID = SEARCHED_PROFILE.CHAT_ID " +
            "        AND SEARCHED_PROFILE_PREF.SEX = " +
            "                                        (SELECT SEX FROM DATING.USER_PROFILE AS USER_PROF " +
            "                                         WHERE USER_PROF.CHAT_ID = :chatId)) " +
            "AND NOT EXISTS " +
            "       (SELECT * FROM DATING.FAVOURITES AS USER_FAV " +
            "        WHERE USER_FAV.FROM_CHAT_ID = :chatId " +
            "        AND USER_FAV.TO_CHAT_ID = SEARCHED_PROFILE.CHAT_ID)")
    List<UserProfile> searchProfiles(@Param("chatId") Long chatId);
}
