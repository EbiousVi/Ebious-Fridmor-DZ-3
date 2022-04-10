package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Relation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FavouriteCollectorTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    /**
     * В тестовых данных пользователь с chatId = 100L
     * Любим вами - к пользователю 500L, 700L
     * Вы любимы - к пользователю 500L, 600L
     * Взаимность - к пользователю 500L
     * Взаимность имеет приоритет, в результирующем наборе будет Вы любимы и Любим Вами будут без пользователя 500L
     */
    @Test
    void collectFavourites() {
        List<SuggestionProfileDto> foo = favouritesCollector.collectFavourites(100L);
        assertThat(foo)
                .anyMatch(dto -> dto.getChatId().equals(500L) && dto.getStatus().equals(Relation.MATCHES.value))
                .anyMatch(dto -> dto.getChatId().equals(600L) && dto.getStatus().equals(Relation.ME.value))
                .anyMatch(dto -> dto.getChatId().equals(700L) && dto.getStatus().equals(Relation.MY.value));
    }
}