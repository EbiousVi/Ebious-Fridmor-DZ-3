package ru.liga.prereformdatingserver.service.search;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;
import ru.liga.prereformdatingserver.service.profile.UserProfileService;
import ru.liga.prereformdatingserver.service.repository.SearchProfileRepository;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {

    private final SearchProfileRepository searchProfileRepository;
    private final StorageService storage;

    public List<ProfileDto> searchProfiles(Long chatId) {
        /*UserProfile authUserProfile = userProfileService.getAuthUserProfile();*/
        return searchProfileRepository.searchProfiles(chatId).stream()
                .map(this::mapper)
                .collect(Collectors.toList());
    }

    private ProfileDto mapper(SearchProfileProjection profile) {
        System.out.println(profile);
        return ProfileDto.builder()
                .chatId(profile.getChatId())
                .name(profile.getName())
                .sex(profile.getSex())
                .avatar(storage.findAvatarAsByteArray(profile.getAvatar()))
                .isMatch(profile.getIsPotentialMatch())
                .build();
    }
}
