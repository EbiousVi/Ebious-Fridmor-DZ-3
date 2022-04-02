package ru.liga.prereformdatingserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.favouriteDto.FavouritesDto;
import ru.liga.prereformdatingserver.domain.dto.profileDto.FavouritesProfileDto;
import ru.liga.prereformdatingserver.service.dao.favourites.FavouritesCollector;
import ru.liga.prereformdatingserver.service.dao.favourites.FavouritesService;

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
    public void chooseAFavorite(@RequestBody FavouritesDto favouritesDto, Pageable pageable) {
        favouritesService.chooseAFavorite(favouritesDto.getFromChatId(), favouritesDto.getToChatId());
    }

    @GetMapping("/favourites/{chatId}")
    public List<FavouritesProfileDto> getAllFavourites(@PathVariable("chatId") Long chatId) {
        return favouritesCollector.collect(chatId);
    }
}
