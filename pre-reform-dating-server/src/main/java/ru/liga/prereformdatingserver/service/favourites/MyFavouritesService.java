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
public class MyFavouritesService {

    private static final Favourites MY = Favourites.MY;
    private final UserProfileRepository userProfileRepository;
    private final FavouritesProfileDtoMapper favouritesProfileDtoMapper;

    @Autowired
    public MyFavouritesService(UserProfileRepository userProfileRepository,
                               FavouritesProfileDtoMapper favouritesProfileDtoMapper) {
        this.userProfileRepository = userProfileRepository;
        this.favouritesProfileDtoMapper = favouritesProfileDtoMapper;
    }

    /**
     * Мои лайки
     */
    public List<FavouritesProfileDto> getMyFavourites(Long chatId) {
        return userProfileRepository.findMyFavourites(chatId)
                .stream()
                .map(profile -> favouritesProfileDtoMapper.map(profile, MY))
                .collect(Collectors.toList());
    }
}
