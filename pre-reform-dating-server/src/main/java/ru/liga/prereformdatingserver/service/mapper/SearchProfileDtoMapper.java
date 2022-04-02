package ru.liga.prereformdatingserver.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.SearchProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import javax.annotation.PostConstruct;

@Service
public class SearchProfileDtoMapper {

    private final StorageService storage;
    private final ModelMapper mapper;

    @Autowired
    public SearchProfileDtoMapper(StorageService storage, ModelMapper mapper) {
        this.storage = storage;
        this.mapper = mapper;
    }

    @PostConstruct
    public void postConstruct() {
        mapper.createTypeMap(UserProfile.class, SearchProfileDto.class);
    }

    public SearchProfileDto map(UserProfile userProfile, Favourites favourites) {
        TypeMap<UserProfile, SearchProfileDto> typeMap = mapper.getTypeMap(UserProfile.class, SearchProfileDto.class);
        typeMap.addMapping(UserProfile::getChatId, SearchProfileDto::setChatId);
        typeMap.addMapping(UserProfile::getName, SearchProfileDto::setName);
        typeMap.addMapping(UserProfile::getSex, SearchProfileDto::setSex);
        SearchProfileDto searchProfileDto = mapper.map(userProfile, SearchProfileDto.class);
        searchProfileDto.setFavourites(favourites);
        searchProfileDto.setImage(storage.avatarToByteArray(userProfile.getAvatar()));
        return searchProfileDto;
    }
}
