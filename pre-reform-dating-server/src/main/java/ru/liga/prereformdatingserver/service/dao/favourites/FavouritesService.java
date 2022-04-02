package ru.liga.prereformdatingserver.service.dao.favourites;

import ch.qos.logback.core.joran.conditional.IfAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.entity.Favourites;
import ru.liga.prereformdatingserver.service.dao.repository.FavouritesRepository;

@Service
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;

    @Autowired
    public FavouritesService(FavouritesRepository favouritesRepository) {
        this.favouritesRepository = favouritesRepository;
    }

    public void chooseAFavorite(Long fromChatId, Long toChatId) {
        Favourites favourites = new Favourites();
        favourites.setFromChatId(fromChatId);
        favourites.setToChatId(toChatId);
        try {
            favouritesRepository.save(favourites);
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                throw new RuntimeException(e.getCause().getCause().getMessage());
            }
        }
    }

    public Boolean checkMatches(Long fromChatId, Long toChatId) {
        return favouritesRepository.checkMatches(fromChatId, toChatId);
    }
}
