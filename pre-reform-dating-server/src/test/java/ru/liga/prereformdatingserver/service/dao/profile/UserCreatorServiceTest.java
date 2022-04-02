package ru.liga.prereformdatingserver.service.dao.profile;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.dto.profileDto.CreateProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class UserCreatorServiceTest extends PostgresContainer {

    @Autowired
    UserCreatorService userCreatorService;

    @Test
    void createProfile() throws IOException {
        NewProfileDto newProfileDto = new NewProfileDto(100_000L, "U_100_000", "U_100_000", Sex.MALE, List.of(Sex.MALE));
        CreateProfileDto createProfileDto = userCreatorService.createProfile(newProfileDto);
        Assertions.assertThat(createProfileDto)
                .hasFieldOrPropertyWithValue("chatId", newProfileDto.getChatId())
                .hasFieldOrPropertyWithValue("name", newProfileDto.getName())
                .hasFieldOrPropertyWithValue("sex", newProfileDto.getSex().name);
    }


    @Test
    void createProfil2e() {
        NewProfileDto user = userCreatorService.getUser(100L);
        System.out.println(user);
    }

}