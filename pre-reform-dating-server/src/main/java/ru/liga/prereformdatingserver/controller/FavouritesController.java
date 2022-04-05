package ru.liga.prereformdatingserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.prereformdatingserver.domain.dto.favourites.req.FavouritesDto;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.favourites.FavouritesCollector;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dating-server")
@AllArgsConstructor
public class FavouritesController {

    private final FavouritesService favouritesService;
    private final FavouritesCollector favouritesCollector;

    @GetMapping("/favourites/{toChatId}")
    public void chooseAFavorite(@PathVariable("toChatId") Long chatId, Principal principal) {
        favouritesService.setAFavorite(Long.parseLong(principal.getName()), chatId);
    }

    @GetMapping("/favourites")
    public List<ProfileDto> getAllFavourites(Principal principal) {
        return favouritesCollector.collectAllFavourites(Long.parseLong(principal.getName()));
    }
}
