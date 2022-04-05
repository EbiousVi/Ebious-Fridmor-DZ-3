package ru.liga.prereformdatingserver.service.profile;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.security.JwtTokenProvider;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.mapper.UserProfileDtoMapper;
import ru.liga.prereformdatingserver.service.outer.avatar.Domain;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileCreatorService {

    private final RestTranslatorService restTranslatorService;
    private final RestAvatarService restAvatarService;
    private final UserProfileService userProfileService;
    private final UserProfileDtoMapper userProfileDtoMapper;
    private final FavouritesService favouritesService;
    private final StorageService storageService;

    @Transactional
    public UserProfileDto createProfile(NewProfileDto dto) {
        Domain domain = restTranslatorService.translateToObject(dto.getDescription());
        dto.setDescription(domain.getTittle() + domain.getBody());
        dto.setAvatar(restAvatarService.createAvatar(domain));
        UserProfile userProfile = userProfileService.createUserProfile(dto);
        favouritesService.raisePopularity(userProfile.getChatId());
        return userProfileDtoMapper.map(userProfile);
    }

    @Transactional
    public void updateProfile(Long chatId, NewProfileDto dto) {
        UserProfile profile = userProfileService.getUserProfileByChatId(chatId);
   /*     if (!profile.getDescription().equals(dto.getDescription())) {
            String description = restTranslatorService.translateToString(dto.getDescription());
            dto.setDescription(description);
            Path avatar = restAvatarService.createAvatar(description);
            dto.setAvatar(avatar);
        }*/
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
                .avatar(storageService.findAvatarAsByteArray(userProfileByChatId.getAvatar()))
                .build();
    }
}
