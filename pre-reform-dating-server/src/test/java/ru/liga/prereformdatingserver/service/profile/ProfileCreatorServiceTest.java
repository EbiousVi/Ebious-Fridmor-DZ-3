package ru.liga.prereformdatingserver.service.profile;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.List;

class ProfileCreatorServiceTest extends PostgresContainer {

    @Autowired
    ProfileCreatorService profileCreatorService;

    @Test
    void registerUserProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100_000L)
                .name("U_100_000")
                .sex(Sex.FEMALE)
                .description("Врач-терапевт")
                .preferences(List.of(Sex.MALE))
                .build();

        UserProfileDto profileDto = profileCreatorService.registerUserProfile(dto);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profileDto.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(profileDto.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profileDto.getSex()).isEqualTo(dto.getSex());
        assertions.assertThat(profileDto.getDescription()).isEqualTo(dto.getDescription());
        assertions.assertAll();
    }
}
