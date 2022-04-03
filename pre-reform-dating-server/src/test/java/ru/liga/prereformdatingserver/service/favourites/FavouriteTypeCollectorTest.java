package ru.liga.prereformdatingserver.service.favourites;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;

import java.util.List;

class FavouriteTypeCollectorTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    @Test
    void collectAllFavourites() {
        List<FavouritesProfileDto> collect = favouritesCollector.collectAllFavourites(100L);
        Assertions.assertThat(collect).hasSize(4);
    }
}