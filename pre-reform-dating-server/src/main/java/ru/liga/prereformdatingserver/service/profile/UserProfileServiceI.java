package ru.liga.prereformdatingserver.service.profile;

import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;

public interface UserProfileServiceI {

    UserProfile getAuthUserProfile();

    UserProfile getUserProfileByChatId(Long chatId);

    UserProfile createUserProfile(NewProfileDto dto);

    UserProfile updateUserProfile(NewProfileDto dto);

    void deleteUserProfile();
}
