package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;

class SearchProjectionMapperTest extends PostgresContainer {

    @Autowired
    SearchProjectionMapper searchProjectionMapper;

    @Test
    void mapSearchProjectionToDto() {
        SearchProfileProjection expected = new SearchProfileProjection(
                100L, "U_100", Sex.MALE.name, "U_100_DESCRIPTION", true);

        SuggestionProfileDto dto = searchProjectionMapper.map(expected);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(dto.getChatId()).isEqualTo(expected.getChatId());
        assertions.assertThat(dto.getName()).isEqualTo(expected.getName());
        assertions.assertThat(dto.getSex()).isEqualTo(expected.getSex());
        assertions.assertThat(dto.getIsMatch()).isEqualTo(expected.getIsPotentialMatch());
        assertions.assertAll();
    }
}