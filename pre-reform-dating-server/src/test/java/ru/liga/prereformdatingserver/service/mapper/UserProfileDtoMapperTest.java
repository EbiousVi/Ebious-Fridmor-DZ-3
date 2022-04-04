package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.Set;

class UserProfileDtoMapperTest extends PostgresContainer {

    @Autowired
    UserProfileDtoMapper userProfileDtoMapper;

    @Test
    void userProfileDtoMapper() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_description")
                .avatar("1.jpg")
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name))).build();
        UserProfileDto map = userProfileDtoMapper.map(expected);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(map.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(map.getName()).isEqualTo(expected.getName());
        assertions.assertThat(map.getSex()).isEqualTo(Sex.getByValue(expected.getSex()));
        assertions.assertThat(map.getDescription()).isEqualTo(expected.getDescription());
        assertions.assertThat(map.getAvatar()).hasSizeGreaterThan(1);
        assertions.assertThat(map.getPreferences()).hasSize(expected.getPreferences().size());
        assertions.assertAll();
    }
}