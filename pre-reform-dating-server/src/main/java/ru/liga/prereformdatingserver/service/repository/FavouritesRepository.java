package ru.liga.prereformdatingserver.service.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.prereformdatingserver.domain.entity.Favourites;

@Repository
public interface FavouritesRepository extends CrudRepository<Favourites, Long> {

    @Query("SELECT EXISTS " +
            "       (SELECT * FROM DATING.FAVOURITES " +
            "        WHERE FROM_CHAT_ID = :from AND TO_CHAT_ID = :to)")
    Boolean checkMatches(@Param("from") Long from, @Param("to") Long to);



}
