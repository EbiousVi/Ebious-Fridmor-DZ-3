package ru.liga.prereformdatingserver.service.profile;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import ru.liga.prereformdatingserver.service.outer.avatar.Domain;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Test
    void createProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100_000L)
                .name("U_100_000")
                .sex(Sex.FEMALE)
                .description("Врач-терапевт возрасте 48 лет уравновешенная, доброжелательная, обаятельная женщина, держится вхорошей спортивной форме, сын взрослый, живет отдельно, познакомится порядочным человеком мужественной профессии, бывшим военнослужащим».")
                .avatar(Path.of("1.jpg"))
                .preferences(List.of(Sex.MALE))
                .build();
        when(restTranslatorService.translateToObject(dto.getDescription())).thenReturn(new Domain("", ""));
        when(restAvatarService.createAvatar(new Domain("", ""))).thenReturn(Paths.get("1.jpg"));
        UserProfileDto profileDto = profileCreatorService.createProfile(dto);
        System.out.println(profileCreatorService.getProfileDtoByChatId(dto.getChatId()).getDescription());
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profileDto.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(profileDto.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profileDto.getSex()).isEqualTo(dto.getSex());
        assertions.assertAll();
    }


    @Test
    void getProfileDto() {
        Long expectedChatId = 100L;
        UserProfileDto profileDtoByChatId = profileCreatorService.getProfileDtoByChatId(expectedChatId);
        assertThat(profileDtoByChatId.getChatId()).isEqualTo(expectedChatId);
    }

    @Test
    void update() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100L)
                .name("NOT 100 NOW")
                .sex(Sex.FEMALE)
                .description("NOT 100 DESCRIPTION")
                .avatar(Path.of("2.jpg"))
                .preferences(List.of(Sex.MALE))
                .build();
        when(restTranslatorService.translateToObject(dto.getDescription())).thenReturn(new Domain("", ""));
        when(restAvatarService.createAvatar(new Domain("", ""))).thenReturn(Paths.get("1.jpg"));
        profileCreatorService.updateProfile(100L, dto);
    }
}