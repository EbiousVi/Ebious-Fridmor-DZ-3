package ru.liga.prereformdatingserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.favourites.FavouritesDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.FavouritesProfileDto;
import ru.liga.prereformdatingserver.service.favourites.FavouritesCollector;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;

import java.util.List;

@RestController
@RequestMapping("/dating-server")
public class FavouritesController {

    private final FavouritesService favouritesService;
    private final FavouritesCollector favouritesCollector;

    @Autowired
    public FavouritesController(FavouritesService favouritesService, FavouritesCollector favouritesCollector) {
        this.favouritesService = favouritesService;
        this.favouritesCollector = favouritesCollector;
    }

    @PostMapping("/favourites/like")
    public void chooseAFavorite(@RequestBody FavouritesDto favouritesDto) {
        favouritesService.setAFavorite(favouritesDto.getFromChatId(), favouritesDto.getToChatId());
    }

    @GetMapping("/favourites/{chatId}")
    public List<FavouritesProfileDto> getAllFavourites(@PathVariable("chatId") Long chatId) {
        return favouritesCollector.collectAllFavourites(chatId);
    }
}
