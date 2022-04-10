package ru.liga.prereformdatingserver.service.favourites;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Relation;
import ru.liga.prereformdatingserver.service.mapper.FavouritesProfileDtoMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.liga.prereformdatingserver.domain.enums.Relation.*;

@Service
@RequiredArgsConstructor
public class FavouritesCollector {

    private final FavouritesService favouritesService;
    private final FavouritesProfileDtoMapper mapper;

    public List<SuggestionProfileDto> collectFavourites(Long chatId) {
        List<SuggestionProfileDto> matches = mapToDto(favouritesService.getMatchesFavourites(chatId), MATCHES);
        List<SuggestionProfileDto> my = mapToDto(favouritesService.getMyFavourites(chatId), MY);
        List<SuggestionProfileDto> me = mapToDto(favouritesService.getWhoHasMeFavourites(chatId), ME);
        return Stream.of(matches, my, me)
                .flatMap(Collection::stream)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        dto -> List.of(dto.getChatId()),
                                        dto -> dto, BinaryOperator.minBy(Comparator.comparing(SuggestionProfileDto::getStatus))
                                ), map -> new ArrayList<>(map.values())));
    }

    private List<SuggestionProfileDto> mapToDto(List<UserProfile> userProfiles, Relation relation) {
        return userProfiles.stream()
                .map(profile -> mapper.map(profile, relation))
                .collect(Collectors.toList());
    }
}
