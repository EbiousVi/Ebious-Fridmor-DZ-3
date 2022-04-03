package ru.liga.prereformdatingserver.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SearchProfileDto;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;
import ru.liga.prereformdatingserver.service.mapper.SearchProfileDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final UserProfileRepository userProfileService;
    private final SearchProfileDtoMapper searchProfileDtoMapper;
    private final FavouritesService favouritesService;

    @Autowired
    public SearchService(UserProfileRepository userProfileService,
                         SearchProfileDtoMapper searchProfileDtoMapper,
                         FavouritesService favouritesService) {
        this.userProfileService = userProfileService;
        this.searchProfileDtoMapper = searchProfileDtoMapper;
        this.favouritesService = favouritesService;
    }

    public List<SearchProfileDto> searchProfiles(Long chatId) {
        return userProfileService.searchProfiles(chatId)
                .stream()
                .map(profile -> {
                    if (favouritesService.checkPotentialMatches(profile.getChatId(), chatId)) {
                        return searchProfileDtoMapper.map(profile, true);
                    } else {
                        return searchProfileDtoMapper.map(profile, false);
                    }
                })
                .collect(Collectors.toList());
    }
}
