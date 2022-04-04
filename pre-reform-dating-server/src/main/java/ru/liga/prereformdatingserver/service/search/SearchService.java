package ru.liga.prereformdatingserver.service.search;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.mapper.ProfileDtoMapper;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {

    private final UserProfileRepository userProfileService;
    private final FavouritesService favouritesService;
    private final ProfileDtoMapper profileDtoMapper;

    public List<ProfileDto> searchProfiles(Long chatId) {
        return userProfileService.searchProfiles(chatId)
                .stream()
                .map(profile -> {
                    if (favouritesService.checkPotentialMatches(profile.getChatId(), chatId)) {
                        return profileDtoMapper.map(profile, Favourites.MATCHES, true);
                    } else {
                        return profileDtoMapper.map(profile, Favourites.MATCHES, false);
                    }
                })
                .collect(Collectors.toList());
    }
}
