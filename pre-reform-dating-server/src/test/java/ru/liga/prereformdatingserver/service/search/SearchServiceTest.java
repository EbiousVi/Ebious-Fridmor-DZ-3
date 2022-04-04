package ru.liga.prereformdatingserver.service.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;

class SearchServiceTest extends PostgresContainer {

    @Autowired
    SearchService searchService;

    @MockBean
    StorageService storageService;

    /**
     * Пользователь chatId = 100L лайкал девку 500L, 700L => в поиске их быть не должно
     */
    @Test
    void searchProfiles() {
        List<Long> expected = List.of(600L, 800L);
        List<ProfileDto> searchList = searchService.searchProfiles(100L);
        Assertions.assertThat(searchList)
                .hasSize(expected.size())
                .anyMatch(profile -> expected.contains(profile.getChatId()));
    }
}