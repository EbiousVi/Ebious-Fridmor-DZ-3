package ru.liga.prereformdatingserver.service.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.mapper.UserProfileDtoMapper;

@Service
@RequiredArgsConstructor
public class ProfileCreatorService {

    private final UserProfileServiceImpl userProfileServiceImpl;
    private final UserProfileDtoMapper userProfileDtoMapper;
    private final FavouritesService favouritesService;

    @Transactional
    public UserProfileDto registerUserProfile(NewProfileDto dto) {
        UserProfile userProfile = userProfileServiceImpl.saveUserProfile(dto);
        favouritesService.generatePopularityForNewbie(userProfile);
        return userProfileDtoMapper.map(userProfile);
    }
}
