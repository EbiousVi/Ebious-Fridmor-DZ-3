package ru.liga.prereformdatingserver.service.favourites;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class FavouriteServiceTest extends PostgresContainer {

    @Autowired
    FavouritesService favouritesService;

    @Test
    void setFavourite() {
        assertThatCode(() -> favouritesService.setFavourite(800L, 200L)).doesNotThrowAnyException();
    }

    @Test
    void setFavouriteRepeatDoesNotThrowException() {
        assertThatCode(() -> {
                    favouritesService.setFavourite(800L, 200L);
                    favouritesService.setFavourite(800L, 200L);
                    favouritesService.setFavourite(800L, 200L);
                }
        ).doesNotThrowAnyException();
    }

    /**
     * В тестовых данных у пользователя с chatId = 100L два лайка от пользователя c chatId = 500L, 600L
     */
    @Test
    void getWhoHasMeFavourites() {
        int expectedProfileSize = 2;
        long expectedChatId1 = 500L;
        long expectedChatId2 = 600L;
        List<UserProfile> whoseFavouriteAmI = favouritesService.getWhoHasMeFavourites(100L);
        assertThat(whoseFavouriteAmI)
                .hasSize(expectedProfileSize)
                .anyMatch(profile -> profile.getChatId().equals(expectedChatId1))
                .anyMatch(profile -> profile.getChatId().equals(expectedChatId2));
    }

    /**
     * Пользователь с chatId = 100L лайкал пользователя chatId = 500L и chatId = 700L
     */
    @Test
    void getMyFavourites() {
        int expectedProfileSize = 2;
        List<Long> expectedChatId = List.of(500L, 700L);
        List<UserProfile> myFavourites = favouritesService.getMyFavourites(100L);
        assertThat(myFavourites)
                .hasSize(expectedProfileSize)
                .anyMatch(profile -> expectedChatId.contains(profile.getChatId()))
                .anyMatch(profile -> expectedChatId.contains(profile.getChatId()));
    }

    /**
     * Взаимный лайк только у пользователя chatId = 100L и пользователя chatId = 500L
     * получаем анкету chatId = 500L
     */
    @Test
    void getMatchesFavouritesForChatId100() {
        List<UserProfile> matches = favouritesService.getMatchesFavourites(100L);
        Assertions.assertThat(matches).hasSize(1).allMatch(profile -> profile.getChatId().equals(500L));
    }

    /**
     * Взаимный лайк только у пользователя chatId = 100L и пользователя chatId = 500L
     * получаем анкету chatId = 100L
     */
    @Test
    void getMatchesFavouritesForChatId500() {
        List<UserProfile> matches = favouritesService.getMatchesFavourites(500L);
        Assertions.assertThat(matches).hasSize(1).allMatch(profile -> profile.getChatId().equals(100L));
    }
}