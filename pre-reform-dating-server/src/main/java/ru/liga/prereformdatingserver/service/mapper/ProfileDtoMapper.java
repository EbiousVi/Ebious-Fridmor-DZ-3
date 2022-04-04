package ru.liga.prereformdatingserver.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
@AllArgsConstructor
public class ProfileDtoMapper {

    private final StorageService storage;

    public ProfileDto map(UserProfile userProfile, Favourites favourites, Boolean isMatch) {
        return ProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(userProfile.getName())
                .sex(userProfile.getSex())
                .status(favourites.value)
                .isMatch(isMatch)
                .avatar(storage.findAvatarAsByteArray(userProfile.getAvatar()))
                .build();
    }
}