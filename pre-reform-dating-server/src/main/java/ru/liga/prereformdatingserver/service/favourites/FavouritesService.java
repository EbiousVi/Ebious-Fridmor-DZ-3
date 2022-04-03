package ru.liga.prereformdatingserver.service.favourites;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.entity.Favourites;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.service.mapper.FavouritesProfileDtoMapper;
import ru.liga.prereformdatingserver.service.repository.FavouritesRepository;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;

import java.util.List;

@Service
@Slf4j
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public FavouritesService(FavouritesRepository favouritesRepository,
                             UserProfileRepository userProfileRepository,
                             FavouritesProfileDtoMapper mapper) {
        this.favouritesRepository = favouritesRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public void chooseAFavorite(Long fromChatId, Long toChatId) {
        try {
            favouritesRepository.save(new Favourites(fromChatId, toChatId));
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                log.info("Duplicate like from {} to {}", fromChatId, toChatId);
            } else {
                log.error("What's gonna happen?", e);
            }
        }
    }

    public Boolean checkMatches(Long fromChatId, Long toChatId) {
        return favouritesRepository.checkMatches(fromChatId, toChatId);
    }

    public List<UserProfile> getMyFavourites(Long chatId) {
        return userProfileRepository.findMyFavourites(chatId);
    }

    public List<UserProfile> getWhoseFavouriteAmI(Long chatId) {
        return userProfileRepository.findWhoseFavouriteAmI(chatId);
    }

    public List<UserProfile> getMatchesFavourites(Long chatId) {
        return userProfileRepository.findMatches(chatId);
    }
}
