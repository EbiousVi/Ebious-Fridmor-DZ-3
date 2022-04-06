package ru.liga.prereformdatingserver.service.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.profile.UserProfileService;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchServiceTest extends PostgresContainer {

    @Autowired
    SearchService searchService;

    @MockBean
    UserProfileService userProfileService;

    @MockBean
    StorageService storage;

    /**
     * Пользователь chatId = 100L лайкал девку 500L, 700L => в поиске их быть не должно
     */
    @Test
    void searchProfiles() {
        List<Long> expected = List.of(600L, 800L);
        UserProfile userProfile = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_DESCRIPTION")
                .avatar("1.jpg")
                .isNew(true)
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name)))
                .build();
        when(userProfileService.getAuthUserProfile()).thenReturn(userProfile);
        when(storage.findAvatarAsByteArray(userProfile.getAvatar())).thenReturn(new byte[1]);
        List<ProfileDto> searchList = searchService.searchProfiles();
        verify(userProfileService).getAuthUserProfile();
        Assertions.assertThat(searchList)
                .hasSize(expected.size())
                .anyMatch(profile -> expected.contains(profile.getChatId()));
    }
}