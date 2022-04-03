package ru.liga.prereformdatingserver.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.FavouritesProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
public class FavouritesProfileDtoMapper {

    private final StorageService storage;

    @Autowired
    public FavouritesProfileDtoMapper(StorageService storage) {
        this.storage = storage;
    }

    public FavouritesProfileDto map(UserProfile userProfile, Favourites favourites) {
        return FavouritesProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(userProfile.getName())
                .sex(userProfile.getSex())
                .status(favourites.value)
                .avatar(storage.findAvatarAsByteArray(userProfile.getAvatar()))
                .build();
    }
}
