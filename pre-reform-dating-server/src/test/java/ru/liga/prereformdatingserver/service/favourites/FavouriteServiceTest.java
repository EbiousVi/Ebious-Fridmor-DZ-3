package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;

import java.util.List;


class FavouriteServiceTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    @Autowired
    FavouritesService favouritesService;

    @Test
    void chooseAFavourite() {
        favouritesService.chooseAFavorite(800L, 60300L);
    }

    @Test
    void collectAllFavourites() {
        List<FavouritesProfileDto> foo = favouritesCollector.collectAllFavourites(100L);
        foo.forEach((x) -> System.out.println(x.getName() + "=" + x.getChatId() + " = " + x.getFavourites()));
    }
}