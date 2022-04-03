package ru.liga.prereformdatingserver.service.favourites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;
import ru.liga.prereformdatingserver.service.mapper.FavouritesProfileDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchesFavouritesService {

    private static final Favourites MATCHES = Favourites.MATCHES;
    private final UserProfileRepository userProfileRepository;
    private final FavouritesProfileDtoMapper favouriteProfileMapper;

    @Autowired
    public MatchesFavouritesService(UserProfileRepository userProfileRepository,
                                    FavouritesProfileDtoMapper favouriteProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.favouriteProfileMapper = favouriteProfileMapper;
    }

    /**
     * Совпадения
     */
    public List<FavouritesProfileDto> getMatchesFavourites(Long chatId) {
        return userProfileRepository.findMatches(chatId)
                .stream()
                .map(profile -> favouriteProfileMapper.map(profile, MATCHES))
                .collect(Collectors.toList());
    }
}
