package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SearchProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.Set;

class SearchProfileDtoMapperTest extends PostgresContainer {

    @Autowired
    SearchProfileDtoMapper searchProfileDtoMapper;

    @Test
    void searchProfileDtoMapper() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_description")
                .avatar("1.jpg")
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name))).build();
        SearchProfileDto searchProfileDto = searchProfileDtoMapper.map(expected, false);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(searchProfileDto.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(searchProfileDto.getName()).isEqualTo(expected.getName());
        assertions.assertThat(searchProfileDto.getSex()).isEqualTo(expected.getSex());
        assertions.assertThat(searchProfileDto.getIsMatch()).isEqualTo(false);
        assertions.assertThat(searchProfileDto.getAvatar()).hasSizeGreaterThan(1);
        assertions.assertAll();
    }
}