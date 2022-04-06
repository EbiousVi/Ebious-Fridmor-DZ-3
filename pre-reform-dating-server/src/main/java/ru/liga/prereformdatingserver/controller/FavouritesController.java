package ru.liga.prereformdatingserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.favourites.FavouritesCollector;
import ru.liga.prereformdatingserver.service.favourites.FavouritesService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dating-server")
public class FavouritesController {

    private final FavouritesService favouritesService;
    private final FavouritesCollector favouritesCollector;

    @GetMapping("/favourites/{toChatId}")
    public void chooseAFavorite(@PathVariable("toChatId") Long toChatId, Principal principal) {
        favouritesService.setAFavorite(Long.parseLong(principal.getName()), toChatId);
    }

    @GetMapping("/favourites")
    public List<ProfileDto> getAllFavourites(Principal principal) {
        return favouritesCollector.collectAllFavourites(Long.parseLong(principal.getName()));
    }
}
