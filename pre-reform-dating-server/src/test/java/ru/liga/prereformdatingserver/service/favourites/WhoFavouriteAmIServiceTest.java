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

class WhoFavouriteAmIServiceTest extends PostgresContainer {

    @Autowired
    WhoFavouriteAmIService whoFavouriteAmIService;

    @MockBean
    StorageService storage;

    @BeforeEach
    void setUp() {
        byte[] bytes = new byte[1];
        when(storage.findAvatarAsByteArray(Mockito.any())).thenReturn(bytes);
    }

    /**
     * В тестовых данных у пользователя с chatId = 100L два лайка от пользователя c chatId = 500L, 600L
     */
    @Test
    void getWhoseFavouriteAmI() {
        int expectedProfileSize = 2;
        long expectedChatId1 = 500L;
        long expectedChatId2 = 600L;
        List<ProfileDto> whoseFavouriteAmI = whoFavouriteAmIService.getWhoseFavouriteAmI(100L);
        assertThat(whoseFavouriteAmI)
                .hasSize(expectedProfileSize)
                .anyMatch(profile -> profile.getChatId().equals(expectedChatId1))
                .anyMatch(profile -> profile.getChatId().equals(expectedChatId2));
    }
}