package ru.liga.prereformdatingserver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.service.profile.ProfileCreatorService;

@RestController
@RequestMapping("/dating-server")
@AllArgsConstructor
public class UserProfileController {

    private final ProfileCreatorService profileCreatorService;

    @GetMapping("/profiles/{chatId}")
    public UserProfileDto getUserProfile(@PathVariable Long chatId) {
        return profileCreatorService.getProfileDtoByChatId(chatId);
    }

    @PostMapping("/profiles")
    public UserProfileDto registerProfile(@RequestBody NewProfileDto newProfileDto) {
        return profileCreatorService.createProfile(newProfileDto);
    }

    @PutMapping("/profile/{chatId}")
    public void updateUserProfile(@PathVariable Long chatId, @RequestBody NewProfileDto profileDto) {
        profileCreatorService.updateProfile(chatId, profileDto);
    }
}

