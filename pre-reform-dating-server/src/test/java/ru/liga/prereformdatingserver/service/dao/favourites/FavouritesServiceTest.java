package ru.liga.prereformdatingserver.service.dao.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;


class FavouritesServiceTest extends PostgresContainer {

    @Autowired
    FavouritesService favouritesService;

    @Test
    void chooseAFavourite() {
        favouritesService.chooseAFavorite(800L, 600L);
    }
}