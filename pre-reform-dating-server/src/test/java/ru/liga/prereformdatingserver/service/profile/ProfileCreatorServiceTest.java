package ru.liga.prereformdatingserver.service.profile;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.dto.Description;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ProfileCreatorServiceTest extends PostgresContainer {

    @Autowired
    ProfileCreatorService profileCreatorService;

    @MockBean
    RestAvatarService restAvatarService;

    @MockBean
    RestTranslatorService restTranslatorService;

    @MockBean
    StorageService storage;

    @Test
    void createProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100_000L)
                .name("U_100_000")
                .sex(Sex.FEMALE)
                .description("Врач-терапевт возрасте 48 лет уравновешенная, доброжелательная, обаятельная женщина")
                .avatar(Path.of("1.jpg"))
                .preferences(List.of(Sex.MALE))
                .build();
        Description description = new Description("Врач-терапевт", "возрасте 48 лет уравновешенная, доброжелательная, обаятельная женщина");
        when(restTranslatorService.translateDescription(dto.getDescription())).thenReturn(description);
        when(restAvatarService.createAvatar(description)).thenReturn(dto.getAvatar());
        when(storage.findAvatarAsByteArray(dto.getAvatar().toString())).thenReturn(new byte[1]);

        UserProfileDto profileDto = profileCreatorService.createProfile(dto);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profileDto.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(profileDto.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profileDto.getSex()).isEqualTo(dto.getSex());
        assertions.assertThat(profileDto.getDescription()).isEqualTo(description.getTittle() + description.getBody());
        assertions.assertAll();
    }


    @Test
    void getCurrentProfile() {
        Long expectedChatId = 100L;
        UserProfileDto profileDtoByChatId = profileCreatorService.getProfile(expectedChatId);
        assertThat(profileDtoByChatId.getChatId()).isEqualTo(expectedChatId);
    }

    @Test
    void updateProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(1L)
                .name("NOT 100 NOW")
                .sex(Sex.FEMALE)
                .description("Врач-терапевт возрасте 48 лет уравновешенная, доброжелательная, обаятельная женщина")
                .avatar(Path.of("2.jpg"))
                .preferences(List.of(Sex.MALE))
                .build();
        Description description = new Description("Врач-терапевт", "возрасте 48 лет уравновешенная, доброжелательная, обаятельная женщина");
        when(restTranslatorService.translateDescription(dto.getDescription())).thenReturn(description);
        when(restAvatarService.createAvatar(description)).thenReturn(dto.getAvatar());
        when(storage.findAvatarAsByteArray(dto.getAvatar().toString())).thenReturn(new byte[1]);

        UserProfileDto profileDto = profileCreatorService.updateProfile(dto.getChatId(), dto);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profileDto.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(profileDto.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profileDto.getSex()).isEqualTo(dto.getSex());
        assertions.assertThat(profileDto.getDescription()).isEqualTo(description.getTittle() + description.getBody());
        assertions.assertAll();
    }
}