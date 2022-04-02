package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.prereformdatingserver.domain.dto.profileDto.SearchProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.domain.enums.Sex;

@SpringBootTest
class SearchProfileDtoMapperTest {

    @Autowired
    SearchProfileDtoMapper searchProfileDtoMapper;

    @Test
    void map() {
        UserProfile userProfile = new UserProfile(1L, 100L, "U_1", Sex.MALE.name, "U_1_description", "1.jpg", true);
        SearchProfileDto searchProfileDto = searchProfileDtoMapper.map(userProfile, Favourites.MATCHES);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(searchProfileDto.getChatId()).isEqualTo(userProfile.getChatId());
        assertions.assertThat(searchProfileDto.getName()).isEqualTo(userProfile.getName());
        assertions.assertThat(searchProfileDto.getSex()).isEqualTo(userProfile.getSex());
        //assertions.assertThat(searchProfileDto.getResource().exists()).isTrue();
        assertions.assertThat(searchProfileDto.getFavourites()).isEqualTo(Favourites.MATCHES);
        assertions.assertAll();
    }
}