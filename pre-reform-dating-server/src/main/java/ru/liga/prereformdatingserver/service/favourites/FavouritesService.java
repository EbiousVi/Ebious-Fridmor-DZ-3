package ru.liga.prereformdatingserver.service.favourites;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.entity.Favourites;
import ru.liga.prereformdatingserver.service.repository.FavouritesRepository;

@Service
@Slf4j
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;

    @Autowired
    public FavouritesService(FavouritesRepository favouritesRepository) {
        this.favouritesRepository = favouritesRepository;
    }

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

    public Boolean checkPotentialMatches(Long fromChatId, Long toChatId) {
        return favouritesRepository.checkMatches(fromChatId, toChatId);
    }
}
