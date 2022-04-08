package ru.liga.prereformdatingserver.service.favourites;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.entity.Favourites;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.repository.FavouritesRepository;
import ru.liga.prereformdatingserver.repository.UserProfileRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final UserProfileRepository userProfileRepository;

    public void setAFavorite(Long fromChatId, Long toChatId) {
        try {
            favouritesRepository.save(new Favourites(fromChatId, toChatId));
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                log.info("Duplicate like from {} to {}", fromChatId, toChatId, e);
            }
            if (e.getCause() instanceof DataIntegrityViolationException) {
                log.info("Someone chat id not found, from = {}, to {}", fromChatId, toChatId, e);
            }
            log.error("Unexpected exception case", e);
        }
    }

    @Transactional
    public void raisePopularityForNewbie(UserProfile userProfile) {
        List<UserProfile> allProfiles = userProfileRepository.findAll();
        Collections.shuffle(allProfiles);
        int raisePopularityScore = 3;
        allProfiles.stream()
                .filter(profile -> !profile.getChatId().equals(userProfile.getChatId()))
                .filter(profile -> userProfile.getPreferences().stream().anyMatch(pref -> pref.getSex().equals(profile.getSex())))
                .filter(profile -> profile.getPreferences().stream().anyMatch(pref -> pref.getSex().equals(userProfile.getSex())))
                .limit(raisePopularityScore)
                .forEach(profile -> setAFavorite(profile.getChatId(), userProfile.getChatId()));
    }

    public List<UserProfile> getWhoseFavouriteAmI(Long chatId) {
        return userProfileRepository.findWhoseFavouriteAmI(chatId);
    }

    public List<UserProfile> getMyFavourites(Long chatId) {
        return userProfileRepository.findMyFavourites(chatId);
    }

    public List<UserProfile> getMatchesFavourites(Long chatId) {
        return userProfileRepository.findMatches(chatId);
    }
}
