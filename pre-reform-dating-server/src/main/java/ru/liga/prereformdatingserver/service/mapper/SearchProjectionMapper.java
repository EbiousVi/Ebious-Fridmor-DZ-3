package ru.liga.prereformdatingserver.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
@RequiredArgsConstructor
public class SearchProjectionMapper {

    private final StorageService storage;

    public ProfileDto map(SearchProfileProjection profile) {
        return ProfileDto.builder()
                .chatId(profile.getChatId())
                .name(profile.getName())
                .sex(profile.getSex())
                .avatar(storage.findAvatarAsByteArray(profile.getAvatar()))
                .isMatch(profile.getIsPotentialMatch())
                .build();
    }
}
