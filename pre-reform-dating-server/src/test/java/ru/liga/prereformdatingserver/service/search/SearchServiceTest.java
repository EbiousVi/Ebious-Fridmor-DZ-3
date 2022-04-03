package ru.liga.prereformdatingserver.service.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.SearchProfileDto;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.search.SearchService;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;

class SearchServiceTest extends PostgresContainer {

    @Autowired
    SearchService searchService;

    @Autowired
    FavouritesService favouritesService;

    @MockBean
    StorageService storageService;

    @Test
    void foo() {
        //Пользователь chatId = 100L лайкал девку 500L, 700L => в поиске их быть не должно
        List<Long> expected = List.of(600L, 800L);
        List<SearchProfileDto> searchList = searchService.searchProfiles(100L);
        Assertions.assertThat(searchList)
                .hasSize(6)
                .anyMatch(profile -> expected.contains(profile.getChatId()));
    }
}