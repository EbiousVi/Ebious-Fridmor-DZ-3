package ru.liga.prereformdatingserver.service.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.List;
import java.util.Set;

class SearchServiceTest extends PostgresContainer {

    @Autowired
    SearchService searchService;

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
                .isNew(true)
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name)))
                .build();
        List<SuggestionProfileDto> searchList = searchService.searchProfiles(userProfile.getChatId());
        Assertions.assertThat(searchList)
                .hasSize(expected.size())
                .anyMatch(profile -> expected.contains(profile.getChatId()));
    }
}