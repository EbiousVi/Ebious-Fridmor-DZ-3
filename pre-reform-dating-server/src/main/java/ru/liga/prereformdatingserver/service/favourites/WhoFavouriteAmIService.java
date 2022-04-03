package ru.liga.prereformdatingserver.service.favourites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.FavouritesProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;
import ru.liga.prereformdatingserver.service.mapper.FavouritesProfileDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WhoFavouriteAmIService {

    private static final Favourites ME = Favourites.ME;
    private final UserProfileRepository userProfileRepository;
    private final FavouritesProfileDtoMapper favouriteProfileMapper;

    @Autowired
    public WhoFavouriteAmIService(UserProfileRepository userProfileRepository,
                                  FavouritesProfileDtoMapper favouriteProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.favouriteProfileMapper = favouriteProfileMapper;
    }

    /**
     * Кто меня лайкнул
     */
    public List<FavouritesProfileDto> getWhoseFavouriteAmI(Long chatId) {
        return userProfileRepository.findWhoseFavouriteAmI(chatId)
                .stream()
                .map(profile -> favouriteProfileMapper.map(profile, ME))
                .collect(Collectors.toList());
    }
}
