package ru.liga.prereformdatingserver.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;

import java.util.List;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

    List<UserProfile> findAll();

    @Query("SELECT * FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "                  (SELECT TO_CHAT_ID FROM DATING.FAVOURITES " +
            "                   WHERE FROM_CHAT_ID = :chatId)")
    List<UserProfile> findMyFavourites(@Param("chatId") Long chatId);

    @Query("SELECT * FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "                (SELECT FROM_CHAT_ID FROM DATING.FAVOURITES " +
            "                 WHERE TO_CHAT_ID = :chatId)")
    List<UserProfile> findWhoHasMeFavourites(@Param("chatId") Long chatId);

    @Query("SELECT * FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "                (SELECT TO_CHAT_ID FROM DATING.FAVOURITES " +
            "                 WHERE FROM_CHAT_ID = :chatId) " +
            "INTERSECT " +
            "SELECT * FROM DATING.USER_PROFILE " +
            "WHERE CHAT_ID IN " +
            "                 (SELECT FROM_CHAT_ID FROM DATING.FAVOURITES" +
            "                  WHERE TO_CHAT_ID = :chatId)")
    List<UserProfile> findMatches(@Param("chatId") Long chatId);
}
