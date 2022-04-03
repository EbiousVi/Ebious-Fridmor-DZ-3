package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.FavouritesProfileDto;

import java.util.List;


class FavouriteServiceTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    @Autowired
    FavouritesService favouritesService;

    @Test
    void chooseAFavourite() {
        favouritesService.setAFavorite(800L, 200L);
        favouritesService.setAFavorite(800L, 200L);
        favouritesService.setAFavorite(800L, 200L);
    }

    @Test
    void collectAllFavourites() {
        List<FavouritesProfileDto> foo = favouritesCollector.collectAllFavourites(100L);
        foo.forEach((x) -> System.out.println(x.getName() + "=" + x.getChatId() + " = " + x.getStatus()));
    }
}