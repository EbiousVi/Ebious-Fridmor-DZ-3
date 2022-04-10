package ru.liga.prereformdatingserver.service.profile;

import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;

public interface UserProfileServiceI {

    UserProfile getUserProfileById(Long chatId);

    UserProfile saveUserProfile(NewProfileDto dto);

    UserProfile updateUserProfile(Long chatId, NewProfileDto dto);

    void deleteUserProfile(Long chatId);
}
