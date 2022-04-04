package ru.liga.prereformdatingserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.favourites.req.FavouritesDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.favourites.FavouritesCollector;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;

import java.util.List;

@RestController
@RequestMapping("/dating-server")
@AllArgsConstructor
public class FavouritesController {

    private final FavouritesService favouritesService;
    private final FavouritesCollector favouritesCollector;

    @PostMapping("/favourites/like")
    public void chooseAFavorite(@RequestBody FavouritesDto favouritesDto) {
        favouritesService.setAFavorite(favouritesDto.getFromChatId(), favouritesDto.getToChatId());
    }

    @GetMapping("/favourites/{chatId}")
    public List<ProfileDto> getAllFavourites(@PathVariable("chatId") Long chatId) {
        return favouritesCollector.collectAllFavourites(chatId);
    }
}
