package ru.liga.prereformdatingserver.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import javax.annotation.PostConstruct;

@Service
public class FavouritesProfileDtoMapper {

    private final StorageService storage;
    private final ModelMapper mapper;

    @Autowired
    public FavouritesProfileDtoMapper(StorageService storage, ModelMapper mapper) {
        this.storage = storage;
        this.mapper = mapper;
    }

    @PostConstruct
    public void postConstruct() {
        mapper.createTypeMap(UserProfile.class, FavouritesProfileDto.class);
    }

    public FavouritesProfileDto map(UserProfile userProfile, Favourites favourites) {
        TypeMap<UserProfile, FavouritesProfileDto> typeMap = mapper.getTypeMap(UserProfile.class, FavouritesProfileDto.class);
        typeMap.addMapping(UserProfile::getChatId, (FavouritesProfileDto::setChatId));
        typeMap.addMapping(UserProfile::getName, (FavouritesProfileDto::setName));
        typeMap.addMapping(UserProfile::getSex, (FavouritesProfileDto::setSex));
        FavouritesProfileDto favouritesProfileDto = mapper.map(userProfile, FavouritesProfileDto.class);
        favouritesProfileDto.setFavourites(favourites);
        favouritesProfileDto.setAvatar(storage.avatarToByteArray(userProfile.getAvatar()));
        return favouritesProfileDto;
    }
}
