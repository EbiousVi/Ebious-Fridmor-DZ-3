package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Relation;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.Set;

import static org.mockito.Mockito.when;

class FavouritesProfileDtoMapperTest extends PostgresContainer {

    @Autowired
    FavouritesProfileDtoMapper favouritesProfileDtoMapper;

    @MockBean
    StorageService storage;

    @Test
    void profileDtoMapper() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_description")
                .avatar("1.jpg")
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name))).build();
        when(storage.findAvatarAsByteArray(expected.getAvatar())).thenReturn(new byte[2]);
        ProfileDto searchProfileDto = favouritesProfileDtoMapper.map(expected, Relation.MATCHES);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(searchProfileDto.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(searchProfileDto.getName()).isEqualTo(expected.getName());
        assertions.assertThat(searchProfileDto.getSex()).isEqualTo(expected.getSex());
        assertions.assertThat(searchProfileDto.getStatus()).isEqualTo(Relation.MATCHES.value);
        assertions.assertAll();
    }
}