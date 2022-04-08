package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.Set;

import static org.mockito.Mockito.when;

class UserFavouritesProfileDtoMapperTest extends PostgresContainer {

    @Autowired
    UserProfileDtoMapper userProfileDtoMapper;

    @MockBean
    StorageService storage;

    @Test
    void userProfileDtoMapper() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_description")
                .avatar("1.jpg")
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name))).build();
        when(storage.findAvatarAsByteArray(expected.getAvatar())).thenReturn(new byte[2]);
        UserProfileDto userProfileDto = userProfileDtoMapper.map(expected);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userProfileDto.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(userProfileDto.getName()).isEqualTo(expected.getName());
        assertions.assertThat(userProfileDto.getSex()).isEqualTo(Sex.getByValue(expected.getSex()));
        assertions.assertThat(userProfileDto.getDescription()).isEqualTo(expected.getDescription());
        assertions.assertThat(userProfileDto.getAvatar()).hasSizeGreaterThan(1);
        assertions.assertThat(userProfileDto.getPreferences()).hasSize(expected.getPreferences().size());
        assertions.assertAll();
    }
}