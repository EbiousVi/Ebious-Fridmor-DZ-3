package ru.liga.prereformdatingserver.service.favourites;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;

import java.util.List;

class MatchesFavouriteTypeServiceTest extends PostgresContainer {

    @Autowired
    MatchesFavouritesService matchesFavouritesService;

    @Test
    void findMatchesToUserChatId100L() {
        //Взаимный лайк только у пользователя chatId = 100L и пользователя chatId = 500L
        //получаем анкету chatId = 500L
        List<FavouritesProfileDto> matches = matchesFavouritesService.getMatchesFavourites(100L);
        Assertions.assertThat(matches).hasSize(1).allMatch(profile -> profile.getChatId().equals(500L));
    }

    @Test
    void findMatchesToUserChatId500L() {
        //Взаимный лайк только у пользователя chatId = 100L и пользователя chatId = 500L
        //получаем анкету chatId = 100L
        List<FavouritesProfileDto> matches = matchesFavouritesService.getMatchesFavourites(500L);
        Assertions.assertThat(matches).hasSize(1).allMatch(profile -> profile.getChatId().equals(100L));
    }
}