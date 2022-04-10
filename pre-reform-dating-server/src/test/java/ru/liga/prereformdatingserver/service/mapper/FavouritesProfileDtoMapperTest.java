package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Relation;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.Set;

class FavouritesProfileDtoMapperTest extends PostgresContainer {

    @Autowired
    FavouritesProfileDtoMapper favouritesProfileDtoMapper;

    @Test
    void mapFavouritesProfileToDto() {
        UserProfile expected = UserProfile.builder()
                .chatId(100L)
                .name("U_100")
                .sex(Sex.MALE.name)
                .description("U_100_description")
                .preferences(Set.of(new Preferences(100L, Sex.FEMALE.name))).build();

        SuggestionProfileDto dto = favouritesProfileDtoMapper.map(expected, Relation.MATCHES);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(dto.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(dto.getName()).isEqualTo(expected.getName());
        assertions.assertThat(dto.getSex()).isEqualTo(expected.getSex());
        assertions.assertThat(dto.getStatus()).isEqualTo(Relation.MATCHES.value);
        assertions.assertAll();
    }
}