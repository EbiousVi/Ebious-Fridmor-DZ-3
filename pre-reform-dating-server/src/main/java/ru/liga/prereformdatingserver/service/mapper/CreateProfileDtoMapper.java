package ru.liga.prereformdatingserver.service.mapper;

import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.CreateProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.storage.StorageService;

;import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class CreateProfileDtoMapper {

    private final StorageService storage;
    private final ModelMapper mapper;

    @Autowired
    public CreateProfileDtoMapper(StorageService storage, ModelMapper mapper) {
        this.storage = storage;
        this.mapper = mapper;
    }

    @PostConstruct
    public void postConstruct() {
        mapper.createTypeMap(UserProfile.class, CreateProfileDto.class);
    }

    public CreateProfileDto map(UserProfile userProfile) {
        TypeMap<UserProfile, CreateProfileDto> typeMap = mapper.getTypeMap(UserProfile.class, CreateProfileDto.class);
        typeMap.addMapping(UserProfile::getChatId, CreateProfileDto::setChatId);
        typeMap.addMapping(UserProfile::getName, CreateProfileDto::setName);
        typeMap.addMapping(UserProfile::getSex, CreateProfileDto::setSex);
        CreateProfileDto createProfileDto = typeMap.map(userProfile);
        createProfileDto.setImage(storage.avatarToByteArray(userProfile.getAvatar()));
        return createProfileDto;
    }
}
