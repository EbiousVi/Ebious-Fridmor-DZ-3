package ru.liga.prereformdatingserver.service.favourites;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.service.mapper.ProfileDtoMapper;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WhoFavouriteAmIService {

    private static final Favourites ME = Favourites.ME;
    private final UserProfileRepository userProfileRepository;
    private final ProfileDtoMapper mapper;

    public List<ProfileDto> getWhoseFavouriteAmI(Long chatId) {
        return userProfileRepository.findWhoseFavouriteAmI(chatId)
                .stream()
                .map(profile -> mapper.map(profile, ME, false))
                .collect(Collectors.toList());
    }
}
