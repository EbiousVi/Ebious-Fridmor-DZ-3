package ru.liga.prereformdatingserver.service.dao.favourites;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;

import java.util.List;

class FavouritesCollectorTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    @Test
    void collect() {
        List<FavouritesProfileDto> collect = favouritesCollector.collect(100L);
        Assertions.assertThat(collect).hasSize(4);
    }
}