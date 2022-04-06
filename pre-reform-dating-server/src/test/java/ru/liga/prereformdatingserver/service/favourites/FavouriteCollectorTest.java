package ru.liga.prereformdatingserver.service.favourites;

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

class FavouriteCollectorTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    @MockBean
    StorageService storage;
    /**
     * В тестовых данных пользователь с chatId = 100L
     * Любим вами - к пользователю 500L, 700L
     * Вы любимы - к пользователю 500L, 600L
     * Взаимность - к пользователю 500L
     * Взаимность имеет приоритет, в результирующем наборе будет Вы любимы и Любим Вами будут без пользователя 500L
     */
    @Test
    void collectAllFavourites() {
        byte[] bytes = new byte[1];
        when(storage.findAvatarAsByteArray(Mockito.any())).thenReturn(bytes);

        List<ProfileDto> foo = favouritesCollector.collectAllFavourites(100L);
        assertThat(foo)
                .anyMatch(dto -> dto.getChatId().equals(500L) && dto.getStatus().equals("Взаимность"))
                .anyMatch(dto -> dto.getChatId().equals(600L) && dto.getStatus().equals("Вы любимы"))
                .anyMatch(dto -> dto.getChatId().equals(700L) && dto.getStatus().equals("Любим вами"));
    }
}