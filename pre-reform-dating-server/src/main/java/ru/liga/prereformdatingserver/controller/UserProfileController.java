package ru.liga.prereformdatingserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.service.profile.ProfileCreatorService;

import java.security.Principal;

@RestController
@RequestMapping("/dating-server")
@RequiredArgsConstructor
public class UserProfileController {

    private final ProfileCreatorService profileCreatorService;

    @GetMapping("/profile")
    public UserProfileDto getProfile(Principal principal) {
        return profileCreatorService.getProfileDtoByChatId();//change
    }

    @PostMapping("/profiles")
    public UserProfileDto registerProfile(@RequestBody NewProfileDto newProfileDto) {
        return profileCreatorService.createProfile(newProfileDto);
    }

    @PutMapping("/profile")
    public UserProfileDto updateProfile(@RequestBody NewProfileDto profileDto) {
        return profileCreatorService.updateProfile(profileDto);
    }
}

