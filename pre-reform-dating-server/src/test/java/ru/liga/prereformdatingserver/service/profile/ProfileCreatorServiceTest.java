package ru.liga.prereformdatingserver.service.profile;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import ru.liga.prereformdatingserver.service.outer.avatar.Domain;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;
import ru.liga.prereformdatingserver.service.storage.StorageService;

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

    @MockBean
    StorageService storage;

    @MockBean
    private Authentication auth;

    @BeforeEach
    public void initSecurityContext() {
        String authUserProfile = "100";
        when(auth.getName()).thenReturn(authUserProfile);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

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
        when(restAvatarService.createAvatar(new Domain("", ""))).thenReturn(dto.getAvatar());
        when(storage.findAvatarAsByteArray(dto.getAvatar().toString())).thenReturn(new byte[1]);
        UserProfileDto profileDto = profileCreatorService.createProfile(dto);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profileDto.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(profileDto.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profileDto.getSex()).isEqualTo(dto.getSex());
        assertions.assertAll();
    }


    @Test
    void getProfileDto() {
        Long expectedChatId = 100L;
        UserProfileDto profileDtoByChatId = profileCreatorService.getProfileDtoByChatId();
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
        when(restAvatarService.createAvatar(new Domain("", ""))).thenReturn(dto.getAvatar());
        when(storage.findAvatarAsByteArray(dto.getAvatar().toString())).thenReturn(new byte[1]);
        UserProfileDto userProfileDto = profileCreatorService.updateProfile(dto);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userProfileDto.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(userProfileDto.getName()).isEqualTo(dto.getName());
        assertions.assertThat(userProfileDto.getSex()).isEqualTo(dto.getSex());
        assertions.assertAll();
    }
}