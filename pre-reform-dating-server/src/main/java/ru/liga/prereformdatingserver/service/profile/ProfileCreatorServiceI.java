package ru.liga.prereformdatingserver.service.profile;

import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;

public interface ProfileCreatorServiceI {

    UserProfileDto getProfile(Long chatId);

    UserProfileDto createProfile(NewProfileDto dto);

    UserProfileDto updateProfile(Long chatId, NewProfileDto dto);

    void deleteUserProfile(Long chatId);
}
