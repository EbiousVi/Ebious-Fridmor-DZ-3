package ru.liga.prereformdatingserver.service.profile;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.exception.UserProfileException;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserProfileServiceTest extends PostgresContainer {

    @Autowired
    UserProfileService userProfileService;

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
    void getUserProfileByChatId() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_DESCRIPTION")
                .avatar("1.jpg")
                .isNew(true)
                .preferences(Set.of(new Preferences(1L, 100L, Sex.FEMALE.name)))
                .build();
        UserProfile profile = userProfileService.getUserProfileByChatId(expected.getChatId());
        assertThat(profile).isEqualTo(expected);
    }

    @Test
    void getUserProfileByChatIdNotFound() {
        Long nonExistsChatId = 123456789L;
        Assertions.assertThatThrownBy(() -> userProfileService.getUserProfileByChatId(nonExistsChatId))
                .isInstanceOf(UserProfileException.class);
    }

    @Test
    void getUserProfilePreferences() {
        Long chatId = 100L;
        Preferences expected = new Preferences(1L, chatId, Sex.FEMALE.name);
        UserProfile profile = userProfileService.getUserProfileByChatId(100L);
        assertThat(profile.getPreferences()).hasSize(1).containsOnly(expected);
    }

    @Test
    void createUserProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(999_999L)
                .name("U_999_999")
                .sex(Sex.MALE)
                .description("U_999_999_INFO")
                .avatar(Path.of("1.jpg"))
                .preferences(List.of(Sex.FEMALE))
                .build();
        UserProfile userProfile = userProfileService.createUserProfile(dto);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userProfile.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(userProfile.getName()).isEqualTo(dto.getName());
        assertions.assertThat(userProfile.getDescription()).isEqualTo(dto.getDescription());
        assertions.assertThat(userProfile.getSex()).isEqualTo(dto.getSex().name);
        assertions.assertThat(userProfile.getAvatar()).isEqualTo(dto.getAvatar().toString());
        assertions.assertAll();
    }

    @Test
    void createUserProfileDuplicateKey() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100L)
                .name("U_200_000")
                .sex(Sex.FEMALE)
                .description("U_200_000_DESCRIPTION")
                .avatar(Path.of("1.jpg"))
                .preferences(List.of(Sex.MALE))
                .build();
        Assertions.assertThatThrownBy(() -> userProfileService.createUserProfile(dto))
                .isInstanceOf(UserProfileException.class).hasMessage("User profile = 100 already register!");
    }

    @Test
    void updateUserProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100L)
                .name("UPDATE U_100")
                .sex(Sex.FEMALE)
                .description("UPDATE U_100 DESCRIPTION")
                .avatar(Path.of("100.jpg"))
                .preferences(List.of(Sex.MALE))
                .build();
        UserProfile profile = userProfileService.updateUserProfile(dto);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profile.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profile.getSex()).isEqualTo(dto.getSex().name);
        assertions.assertThat(profile.getDescription()).isEqualTo(dto.getDescription());
        assertions.assertThat(profile.getAvatar()).isEqualTo(dto.getAvatar().toString());
        assertions.assertAll();
    }
}