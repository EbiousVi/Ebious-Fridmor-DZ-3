package ru.liga.prereformdatingserver.service.dao.favourites;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WhoFavouriteAmIServiceTest extends PostgresContainer {

    @Autowired
    WhoFavouriteAmIService whoFavouriteAmIService;

    @Test
    void getWhoseFavouriteAmI() {
        int expectedProfileSize = 1;
        long expectedChatId = 500L;
        //В тестовых данных у пользователя с chatId = 100L только один лайк от пользователя c chatId = 500L
        List<FavouritesProfileDto> whoseFavouriteAmI = whoFavouriteAmIService.getWhoseFavouriteAmI(100L);
        assertThat(whoseFavouriteAmI)
                .hasSize(expectedProfileSize)
                .anyMatch(profile -> profile.getChatId().equals(expectedChatId));
    }
}