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
public class MyFavouritesService {

    private static final Favourites MY = Favourites.MY;
    private final UserProfileRepository userProfileRepository;
    private final ProfileDtoMapper mapper;

    public List<ProfileDto> getMyFavourites(Long chatId) {
        return userProfileRepository.findMyFavourites(chatId)
                .stream()
                .map(profile -> mapper.map(profile, MY, false))
                .collect(Collectors.toList());
    }
}
