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
import ru.liga.prereformdatingserver.service.repository.FavouritesRepository;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void setAFavorite(Long fromChatId, Long toChatId) {
        try {
            favouritesRepository.save(new Favourites(fromChatId, toChatId));
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                log.info("Duplicate like from {} to {}", fromChatId, toChatId);
            } else if (e.getCause() instanceof DataIntegrityViolationException) {
                log.warn("Chat id to {} not found at system", toChatId, e);
            } else {
                log.error("What's gonna happen?", e);
            }
        }
    }

    @Transactional
    public void raisePopularityForNewbie(UserProfile userProfile) {
        List<UserProfile> allProfiles = userProfileRepository.findAll();
        Collections.shuffle(allProfiles);
        int raisePopularityScore = 3;
        allProfiles.stream()
                .filter(profile -> !profile.getChatId().equals(userProfile.getChatId()))
                .filter(profile -> profile.getPreferences().stream().anyMatch(pref -> pref.getSex().equals(userProfile.getSex())))
                .limit(raisePopularityScore)
                .forEach(profile -> setAFavorite(profile.getChatId(), userProfile.getChatId()));
    }
}
