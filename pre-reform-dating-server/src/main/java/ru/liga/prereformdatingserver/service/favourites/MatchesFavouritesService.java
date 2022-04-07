package ru.liga.prereformdatingserver.service.favourites;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.mapper.ProfileDtoMapper;
import ru.liga.prereformdatingserver.repository.UserProfileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchesFavouritesService {

    private static final Favourites MATCHES = Favourites.MATCHES;
    private final UserProfileRepository userProfileRepository;
    private final ProfileDtoMapper mapper;

    public List<ProfileDto> getMatchesFavourites(Long chatId) {
        return userProfileRepository.findMatches(chatId)
                .stream()
                .map(profile -> mapper.map(profile, MATCHES))
                .collect(Collectors.toList());
    }
}
