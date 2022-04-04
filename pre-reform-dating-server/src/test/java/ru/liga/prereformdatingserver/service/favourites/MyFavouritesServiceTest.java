package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MyFavouritesServiceTest extends PostgresContainer {

    @Autowired
    MyFavouritesService myFavouritesService;

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