package ru.liga.prereformdatingserver.service.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.prereformdatingserver.domain.dto.profileDto.CreateProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.io.IOException;

@SpringBootTest
class CreateProfileDtoMapperTest {

    @Autowired
    CreateProfileDtoMapper createProfileDtoMapper;

    @Test
    void map() throws IOException {
        UserProfile userProfile = new UserProfile(1L, 100L, "U_1", Sex.MALE.name, "U_1_description", "1.jpg", true);
        CreateProfileDto createProfileDto = createProfileDtoMapper.map(userProfile);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(createProfileDto.getChatId()).isEqualTo(userProfile.getChatId());
        assertions.assertThat(createProfileDto.getName()).isEqualTo(userProfile.getName());
        assertions.assertThat(createProfileDto.getSex()).isEqualTo(userProfile.getSex());
        //assertions.assertThat(createProfileDto.getResource().exists()).isTrue();
        assertions.assertAll();
    }
}