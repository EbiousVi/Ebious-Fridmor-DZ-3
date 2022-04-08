package ru.liga.prereformdatingserver.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Relation;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
@RequiredArgsConstructor
public class FavouritesProfileDtoMapper {

    private final StorageService storage;

    public ProfileDto map(UserProfile userProfile, Relation relation) {
        return ProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(userProfile.getName())
                .sex(userProfile.getSex())
                .status(relation.value)
                .isMatch(false)
                .avatar(storage.findAvatarAsByteArray(userProfile.getAvatar()))
                .build();
    }
}
