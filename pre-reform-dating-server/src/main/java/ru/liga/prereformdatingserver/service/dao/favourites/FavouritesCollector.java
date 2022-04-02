package ru.liga.prereformdatingserver.service.dao.favourites;

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

    public List<FavouritesProfileDto> collect(Long chatId) {
        List<FavouritesProfileDto> favouritesProfileDtos = new ArrayList<>();
        favouritesProfileDtos.addAll(myFavouritesService.getMyFavourites(chatId));
        favouritesProfileDtos.addAll(whoFavouriteAmIService.getWhoseFavouriteAmI(chatId));
        favouritesProfileDtos.addAll(matchesFavouritesService.getMatchesFavourites(chatId));
        return favouritesProfileDtos;
    }
}
