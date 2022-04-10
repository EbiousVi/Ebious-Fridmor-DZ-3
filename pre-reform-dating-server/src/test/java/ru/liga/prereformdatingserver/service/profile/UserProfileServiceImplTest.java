package ru.liga.prereformdatingserver.service.profile;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.exception.UserProfileException;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserProfileServiceImplTest extends PostgresContainer {

    @Autowired
    UserProfileServiceImpl userProfileServiceImpl;

    @Test
    void getUserProfileByChatId() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .password("password")
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_DESCRIPTION")
                .isNew(true)
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name)))
                .build();
        UserProfile profile = userProfileServiceImpl.getUserProfileById(expected.getChatId());
        assertThat(profile).isEqualTo(expected);
    }

    @Test
    void getUserProfileByChatIdNotFound() {
        Long nonExistsChatId = 123456789L;
        Assertions.assertThatThrownBy(() -> userProfileServiceImpl.getUserProfileById(nonExistsChatId))
                .isInstanceOf(UserProfileException.class);
    }

    @Test
    void getUserProfilePreferences() {
        Long chatId = 100L;
        Preferences expected = new Preferences( chatId, Sex.FEMALE.name);
        UserProfile profile = userProfileServiceImpl.getUserProfileById(100L);
        assertThat(profile.getPreferences()).hasSize(1).containsOnly(expected);
    }

    @Test
    void createUserProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(999_999L)
                .name("U_999_999")
                .sex(Sex.MALE)
                .description("U_999_999_INFO")
                .preferences(List.of(Sex.FEMALE))
                .build();

        UserProfile userProfile = userProfileServiceImpl.saveUserProfile(dto);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userProfile.getChatId()).isEqualTo(dto.getChatId());
        assertions.assertThat(userProfile.getName()).isEqualTo(dto.getName());
        assertions.assertThat(userProfile.getDescription()).isEqualTo(dto.getDescription());
        assertions.assertThat(userProfile.getSex()).isEqualTo(dto.getSex().name);
        assertions.assertAll();
    }

    @Test
    void createUserProfileDuplicateKey() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100L)
                .name("U_200_000")
                .sex(Sex.FEMALE)
                .description("U_200_000_DESCRIPTION")
                .preferences(List.of(Sex.MALE))
                .build();
        Assertions.assertThatThrownBy(() -> userProfileServiceImpl.saveUserProfile(dto))
                .isInstanceOf(UserProfileException.class).hasMessage("User profile = 100 already register!");
    }

    @Test
    void updateUserProfile() {
        NewProfileDto dto = NewProfileDto.builder()
                .chatId(100L)
                .name("UPDATE U_100")
                .sex(Sex.FEMALE)
                .description("UPDATE U_100 DESCRIPTION")
                .preferences(List.of(Sex.MALE))
                .build();

        UserProfile profile = userProfileServiceImpl.updateUserProfile(dto.getChatId(), dto);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(profile.getName()).isEqualTo(dto.getName());
        assertions.assertThat(profile.getSex()).isEqualTo(dto.getSex().name);
        assertions.assertThat(profile.getDescription()).isEqualTo(dto.getDescription());
        assertions.assertAll();
    }
}