package ru.liga.prereformdatingserver.service.profile;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.dto.profileDto.CreateProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profileDto.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.mapper.CreateProfileDtoMapper;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class ProfileCreatorService {

    private final RestTranslatorService restTranslatorService;
    private final RestAvatarService restAvatarService;
    private final UserProfileService userProfileService;
    private final CreateProfileDtoMapper createProfileDtoMapper;

    @Autowired
    public ProfileCreatorService(RestTranslatorService restTranslatorService, RestAvatarService restAvatarService,
                                 UserProfileService userProfileService, CreateProfileDtoMapper createProfileDtoMapper) {
        this.restTranslatorService = restTranslatorService;
        this.restAvatarService = restAvatarService;
        this.userProfileService = userProfileService;
        this.createProfileDtoMapper = createProfileDtoMapper;
    }

    @Transactional
    public CreateProfileDto createProfile(NewProfileDto dto) {
       /* String description = restTranslatorService.translateIntoPreReformDialect(dto.getDescription());
        dto.setDescription(description);
        Path avatar = restAvatarService.createAvatar(dto.getDescription());
        dto.setAvatar(avatar);*/
        dto.setAvatar(Path.of("1.jpg"));
        UserProfile userProfile = userProfileService.createUserProfile(dto);
        return createProfileDtoMapper.map(userProfile);
    }

    @Transactional
    public void updateProfile(Long chatId, NewProfileDto dto) {
        UserProfile profile = userProfileService.getUserProfileByChatId(chatId);
        if (!profile.getDescription().equals(dto.getDescription())) {
            String description = restTranslatorService.translateIntoPreReformDialect(dto.getDescription());
            dto.setDescription(description);
            Path avatar = restAvatarService.createAvatar(description);
            dto.setAvatar(avatar);
        }
        userProfileService.updateUserProfile(chatId, dto);
    }

    public UserProfileDto getProfileDtoByChatId(Long chatId) {
        UserProfile userProfileByChatId = userProfileService.getUserProfileByChatId(chatId);
        return UserProfileDto
                .builder()
                .chatId(userProfileByChatId.getChatId())
                .name(userProfileByChatId.getName())
                .sex(Sex.getByValue(userProfileByChatId.getSex()))
                .description(userProfileByChatId.getDescription())
                .preferences(userProfileByChatId.getPreferences().stream()
                        .map(pref -> Sex.getByValue(pref.getSex()))
                        .collect(Collectors.toList()))
                .build();
    }
}
