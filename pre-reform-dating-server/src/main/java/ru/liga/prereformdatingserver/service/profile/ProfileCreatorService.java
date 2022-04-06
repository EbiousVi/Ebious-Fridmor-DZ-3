package ru.liga.prereformdatingserver.service.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.mapper.UserProfileDtoMapper;
import ru.liga.prereformdatingserver.service.outer.avatar.Domain;
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
    public UserProfileDto getProfileDtoByChatId() {
        UserProfile authProfile = userProfileService.getAuthUserProfile();
        return userProfileDtoMapper.map(authProfile);
    }

    @Override
    @Transactional
    public UserProfileDto createProfile(NewProfileDto dto) {
        workWithOuterService(dto);
        UserProfile userProfile = userProfileService.createUserProfile(dto);
        favouritesService.raisePopularityForNewbie(userProfile);
        return userProfileDtoMapper.map(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(NewProfileDto dto) {
        workWithOuterService(dto);
        UserProfile update = userProfileService.updateUserProfile(dto);
        return userProfileDtoMapper.map(update);
    }

    @Override
    @Transactional
    public void deleteUserProfile() {
        userProfileService.deleteUserProfile();
    }

    private void workWithOuterService(NewProfileDto dto) {
        Domain domain = restTranslatorService.translateToObject(dto.getDescription());
        dto.setDescription(domain.getTittle() + domain.getBody());
        dto.setAvatar(restAvatarService.createAvatar(domain));
    }
}
