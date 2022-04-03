package ru.liga.prereformdatingserver.service.favourites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouritesCollector {

    private final WhoFavouriteAmIService whoFavouriteAmIService;
    private final MatchesFavouritesService matchesFavouritesService;
    private final MyFavouritesService myFavouritesService;

    @Autowired
    public FavouritesCollector(WhoFavouriteAmIService whoFavouriteAmIService,
                               MatchesFavouritesService matchesFavouritesService,
                               MyFavouritesService myFavouritesService) {
        this.whoFavouriteAmIService = whoFavouriteAmIService;
        this.matchesFavouritesService = matchesFavouritesService;
        this.myFavouritesService = myFavouritesService;
    }

    public List<FavouritesProfileDto> collectAllFavourites(Long chatId) {
        List<FavouritesProfileDto> collect = new ArrayList<>();
        collect.addAll(matchesFavouritesService.getMatchesFavourites(chatId));
        collect.addAll(myFavouritesService.getMyFavourites(chatId));
        collect.addAll(whoFavouriteAmIService.getWhoseFavouriteAmI(chatId));
        return collect;
    }
}
