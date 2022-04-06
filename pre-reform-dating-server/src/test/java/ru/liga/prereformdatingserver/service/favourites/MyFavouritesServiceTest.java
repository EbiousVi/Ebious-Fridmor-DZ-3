package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MyFavouritesServiceTest extends PostgresContainer {

    @Autowired
    MyFavouritesService myFavouritesService;

    @MockBean
    StorageService storage;

    @BeforeEach
    void setUp() {
        byte[] bytes = new byte[1];
        when(storage.findAvatarAsByteArray(Mockito.any())).thenReturn(bytes);
    }

    /**
     * Пользователь с chatId = 100L лайкал пользователя chatId = 500L и chatId = 700L
     */
    @Test
    void findMyFavourites() {
        int expectedProfileSize = 2;
        List<Long> expectedChatId = List.of(500L, 700L);
        List<ProfileDto> myFavourites = myFavouritesService.getMyFavourites(100L);
        assertThat(myFavourites)
                .hasSize(expectedProfileSize)
                .anyMatch(profile -> expectedChatId.contains(profile.getChatId()))
                .anyMatch(profile -> expectedChatId.contains(profile.getChatId()));
    }
}