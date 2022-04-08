package ru.liga.prereformdatingserver.service.favourites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Relation;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class FavouriteCollectorTest extends PostgresContainer {

    @Autowired
    FavouritesCollector favouritesCollector;

    @MockBean
    StorageService storage;

    byte[] avatarMock = new byte[1];

    @BeforeEach
    void setUp() {
        when(storage.findAvatarAsByteArray(Mockito.any())).thenReturn(avatarMock);
    }

    /**
     * В тестовых данных пользователь с chatId = 100L
     * Любим вами - к пользователю 500L, 700L
     * Вы любимы - к пользователю 500L, 600L
     * Взаимность - к пользователю 500L
     * Взаимность имеет приоритет, в результирующем наборе будет Вы любимы и Любим Вами будут без пользователя 500L
     */
    @Test
    void collectAllFavourites() {
        List<ProfileDto> foo = favouritesCollector.collectAllFavourites(100L);
        assertThat(foo)
                .anyMatch(dto -> dto.getChatId().equals(500L) && dto.getStatus().equals(Relation.MATCHES.value))
                .anyMatch(dto -> dto.getChatId().equals(600L) && dto.getStatus().equals(Relation.ME.value))
                .anyMatch(dto -> dto.getChatId().equals(700L) && dto.getStatus().equals(Relation.MY.value));
    }
}