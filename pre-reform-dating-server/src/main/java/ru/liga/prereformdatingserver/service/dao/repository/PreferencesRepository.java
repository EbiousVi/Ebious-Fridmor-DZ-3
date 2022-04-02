package ru.liga.prereformdatingserver.service.dao.repository;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.prereformdatingserver.domain.entity.Preferences;

import java.util.List;


@Repository
public interface PreferencesRepository extends CrudRepository<Preferences, Long> {

    @Query("select * from dating.preferences " +
            "where chat_id = :chatId")
    List<Preferences> asdasd(@Param("chatId") Long chatId);
}
