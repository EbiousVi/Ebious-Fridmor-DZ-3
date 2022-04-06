package ru.liga.prereformdatingserver.service.favourites;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;

import static org.mockito.Mockito.when;

class MatchesFavouriteServiceTest extends PostgresContainer {

    @Autowired
    MatchesFavouritesService matchesFavouritesService;

    @MockBean
    StorageService storage;

    @BeforeEach
    void setUp() {
        byte[] bytes = new byte[1];
        when(storage.findAvatarAsByteArray(Mockito.any())).thenReturn(bytes);
    }

    /**
     * Взаимный лайк только у пользователя chatId = 100L и пользователя chatId = 500L
     * получаем анкету chatId = 500L
     */
    @Test
    void findMatchesToUserChatId100L() {
        List<ProfileDto> matches = matchesFavouritesService.getMatchesFavourites(100L);
        Assertions.assertThat(matches).hasSize(1).allMatch(profile -> profile.getChatId().equals(500L));
    }

    /**
     * Взаимный лайк только у пользователя chatId = 100L и пользователя chatId = 500L
     * получаем анкету chatId = 100L
     */
    @Test
    void findMatchesToUserChatId500L() {
        List<ProfileDto> matches = matchesFavouritesService.getMatchesFavourites(500L);
        Assertions.assertThat(matches).hasSize(1).allMatch(profile -> profile.getChatId().equals(100L));
    }
}