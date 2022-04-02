package ru.liga.prereformdatingserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.profileDto.CreateProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.dao.profile.UserCreatorService;
import ru.liga.prereformdatingserver.service.dao.profile.UserProfileService;


@RestController
@RequestMapping("/dating-server")
public class UserProfileController {

    private final UserCreatorService userCreatorService;
    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserCreatorService userCreatorService,
                                 UserProfileService userProfileService) {
        this.userCreatorService = userCreatorService;
        this.userProfileService = userProfileService;
    }

    @PostMapping("/profiles")
    public CreateProfileDto registerProfile(@RequestBody NewProfileDto newProfileDto) {
        return userCreatorService.createProfile(newProfileDto);
    }

    @GetMapping("/profiles/{chatId}")
    public NewProfileDto registerProfile(@PathVariable Long chatId) {
        return userCreatorService.getUser(chatId);
    }
}

