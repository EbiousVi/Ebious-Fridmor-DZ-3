/*
package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.Set;

class FavouritesProfileDtoMapperTest extends PostgresContainer {

    @Autowired
    FavouritesProfileDtoMapper favouritesProfileMapper;

    @Test
    void map() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_description")
                .avatar("1.jpg")
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name))).build();
        FavouritesProfileDto searchProfileDto = favouritesProfileMapper.map(expected, Favourites.MATCHES);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(searchProfileDto.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(searchProfileDto.getName()).isEqualTo(expected.getName());
        assertions.assertThat(searchProfileDto.getSex()).isEqualTo(expected.getSex());
        assertions.assertThat(searchProfileDto.getStatus()).isEqualTo(Favourites.MATCHES.value);
        assertions.assertThat(searchProfileDto.getAvatar()).hasSizeGreaterThan(1);
        assertions.assertAll();
    }
}*/
