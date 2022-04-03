package ru.liga.prereformdatingserver.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.stream.Collectors;

@Service
public class UserProfileDtoMapper {

    private final StorageService storage;

    @Autowired
    public UserProfileDtoMapper(StorageService storage) {
        this.storage = storage;
    }

    public UserProfileDto map(UserProfile userProfile) {
        return UserProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(userProfile.getName())
                .sex(Sex.getByValue(userProfile.getSex()))
                .description(userProfile.getDescription())
                .preferences(userProfile.getPreferences().stream()
                        .map(pref -> Sex.getByValue(pref.getSex()))
                        .collect(Collectors.toList()))
                .avatar(storage.findAvatarAsByteArray(userProfile.getAvatar()))
                .build();
    }
}
