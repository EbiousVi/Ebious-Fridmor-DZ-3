package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;

import static org.assertj.core.api.Assertions.assertThatCode;

class FavouriteServiceTest extends PostgresContainer {

    @Autowired
    FavouritesService favouritesService;

    @Test
    void chooseAFavourite() {
        assertThatCode(() -> favouritesService.setAFavorite(800L, 200L)).doesNotThrowAnyException();
    }

    /**
     * Повторные лайки не должны вызывать исключений
     */
    @Test
    void chooseAFavouriteRepeatableCase() {
        assertThatCode(() -> {
                    favouritesService.setAFavorite(800L, 200L);
                    favouritesService.setAFavorite(800L, 200L);
                    favouritesService.setAFavorite(800L, 200L);
                }
        ).doesNotThrowAnyException();
    }
}