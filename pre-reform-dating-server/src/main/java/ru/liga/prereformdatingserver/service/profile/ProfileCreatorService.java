package ru.liga.prereformdatingserver.service.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.mapper.UserProfileDtoMapper;
import ru.liga.prereformdatingserver.service.outer.dto.Description;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

@Service
@RequiredArgsConstructor
public class ProfileCreatorService implements ProfileCreatorServiceI {

    private final RestTranslatorService restTranslatorService;
    private final RestAvatarService restAvatarService;
    private final UserProfileService userProfileService;
    private final UserProfileDtoMapper userProfileDtoMapper;
    private final FavouritesService favouritesService;

    @Override
    public UserProfileDto getProfile(Long id) {
        return userProfileDtoMapper.map(userProfileService.getUserProfileById(id));
    }

    @Override
    @Transactional
    public UserProfileDto createProfile(NewProfileDto dto) {
        Description description = restTranslatorService.translateDescription(dto.getDescription());
        dto.setDescription(description.getTittle() + description.getBody());
        dto.setAvatar(restAvatarService.createAvatar(description));
        UserProfile userProfile = userProfileService.createUserProfile(dto);
        favouritesService.raisePopularityForNewbie(userProfile);
        return userProfileDtoMapper.map(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(Long chatId, NewProfileDto dto) {
        Description description = restTranslatorService.translateDescription(dto.getDescription());
        dto.setDescription(description.getTittle() + description.getBody());
        dto.setAvatar(restAvatarService.createAvatar(description));
        UserProfile userProfile = userProfileService.updateUserProfile(chatId, dto);
        return userProfileDtoMapper.map(userProfile);
    }

    @Override
    @Transactional
    public void deleteUserProfile(Long chatId) {
        userProfileService.deleteUserProfile(chatId);
    }
}
