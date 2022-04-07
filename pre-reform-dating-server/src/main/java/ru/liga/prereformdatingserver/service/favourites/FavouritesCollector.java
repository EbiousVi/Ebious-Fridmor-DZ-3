package ru.liga.prereformdatingserver.service.favourites;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FavouritesCollector {

    private final WhoFavouriteAmIService whoFavouriteAmIService;
    private final MatchesFavouritesService matchesFavouritesService;
    private final MyFavouritesService myFavouritesService;

    public List<ProfileDto> collectAllFavourites(Long chatId) {
        return Stream.of(
                        matchesFavouritesService.getMatchesFavourites(chatId),
                        myFavouritesService.getMyFavourites(chatId),
                        whoFavouriteAmIService.getWhoseFavouriteAmI(chatId))
                .flatMap(Collection::stream).collect(Collectors
                        .collectingAndThen(Collectors.toMap(dto -> List.of(dto.getChatId()),
                                        dto -> dto, BinaryOperator.minBy(Comparator.comparing(ProfileDto::getStatus))),
                                map -> new ArrayList<>(map.values())));
    }
}
