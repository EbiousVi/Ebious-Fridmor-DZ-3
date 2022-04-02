package ru.liga.prereformdatingserver.service.dao.profile;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserProfileServiceTest extends PostgresContainer {

    @Autowired
    UserProfileService userProfileService;

    @Test
    void getUserProfileByChatId() {
        UserProfile userProfile = new UserProfile(1L, 100L, "U_100",
                Sex.MALE.name, "U_100 like_to(500,700) like_from(500) mathces(500)",
                "1.jpg", Arrays.asList(new Preferences(null, 100L, Sex.FEMALE.name)), true);
        UserProfile userProfileByChatId = userProfileService.getUserProfileByChatId(100L);
        System.out.println(userProfileByChatId.getPreferences());
        assertThat(userProfileByChatId).isEqualTo(userProfile);
    }

    @Test
    void createUserProfile() {
        NewProfileDto newProfileDto = new NewProfileDto(999_999L, "U_999_999", "U_999_999_INFO", Sex.MALE, List.of(Sex.MALE));
        UserProfile userProfile = userProfileService.createUserProfile(newProfileDto);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userProfile.getChatId()).isEqualTo(newProfileDto.getChatId());
        assertions.assertThat(userProfile.getName()).isEqualTo(newProfileDto.getName());
        assertions.assertThat(userProfile.getDescription()).isEqualTo(newProfileDto.getDescription());
        assertions.assertThat(userProfile.getSex()).isEqualTo(newProfileDto.getSex().name);
        assertions.assertAll();
    }

/*    @Test
    void update() {
        NewProfileDto newProfileDto = new NewProfileDto(999_999L, "U_999_999", "U_999_999_INFO", Sex.MALE, List.of(Sex.MALE.name));
        UserProfile userProfile = userProfileService.createUserProfile(newProfileDto);
        System.out.println(userProfile);
        NewProfileDto updateDto = new NewProfileDto(999_999L, "111 111 111", "111 111 111", Sex.FEMALE, List.of(Sex.MALE.name));
        UserProfile update = userProfileService.updateUserProfile(newProfileDto);
        System.out.println(update);
    }*/
}