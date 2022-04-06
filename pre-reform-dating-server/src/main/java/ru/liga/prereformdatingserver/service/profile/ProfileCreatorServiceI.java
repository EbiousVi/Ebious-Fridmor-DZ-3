package ru.liga.prereformdatingserver.service.profile;

import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;

public interface ProfileCreatorServiceI {

    UserProfileDto getProfileDtoByChatId();

    UserProfileDto createProfile(NewProfileDto dto);

    UserProfileDto updateProfile(NewProfileDto dto);

    void deleteUserProfile();
}
