package ru.liga.prereformdatingserver.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SearchProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
public class SearchProfileDtoMapper {

    private final StorageService storage;

    @Autowired
    public SearchProfileDtoMapper(StorageService storage) {
        this.storage = storage;
    }

    public SearchProfileDto map(UserProfile userProfile, Boolean isMatch) {
        return SearchProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(userProfile.getName())
                .sex(userProfile.getSex())
                .isMatch(isMatch)
                .avatar(storage.findAvatarAsByteArray(userProfile.getAvatar()))
                .build();
    }
}
