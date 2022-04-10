package ru.liga.prereformdatingserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.service.mapper.UserProfileDtoMapper;
import ru.liga.prereformdatingserver.service.profile.ProfileCreatorService;
import ru.liga.prereformdatingserver.service.profile.UserProfileServiceImpl;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/dating-server")
public class UserProfileController {

    private final ProfileCreatorService profileCreatorService;
    private final UserProfileServiceImpl userProfileService;
    private final UserProfileDtoMapper mapper;

    @GetMapping("/profile")
    public UserProfileDto getUserProfile(Principal principal) {
        long id = Long.parseLong(principal.getName());
        return mapper.map(userProfileService.getUserProfileById(id));
    }

    @PostMapping("/profiles")
    public UserProfileDto registerProfile(@Valid @RequestBody NewProfileDto newProfileDto) {
        return profileCreatorService.registerUserProfile(newProfileDto);
    }

    @PutMapping("/profile")
    public UserProfileDto updateUserProfile(Principal principal,
                                            @Valid @RequestBody NewProfileDto newProfileDto) {
        long id = Long.parseLong(principal.getName());
        return mapper.map(userProfileService.updateUserProfile(id, newProfileDto));
    }
}