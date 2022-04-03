package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.Set;

@SpringBootTest
class FavouriteTypeProfileDtoMapperTest {

    @Autowired
    FavouritesProfileDtoMapper favouriteProfileMapper;

    @Test
    void map() {
        UserProfile userProfile = new UserProfile( 100L, "U_1", Sex.MALE.name, "U_1_description", "1.jpg", Set.of());
        FavouritesProfileDto favourites = favouriteProfileMapper.map(userProfile, Favourites.MATCHES);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(favourites.getChatId()).isEqualTo(userProfile.getChatId());
        assertions.assertThat(favourites.getName()).isEqualTo(userProfile.getName());
        assertions.assertThat(favourites.getSex()).isEqualTo(userProfile.getSex());
        assertions.assertThat(favourites.getFavourites()).isEqualTo(Favourites.MATCHES);
        assertions.assertAll();
    }
}