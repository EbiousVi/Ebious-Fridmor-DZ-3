package ru.liga.prereformdatingserver.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.security.jwt.JwtTokenProvider;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileDtoMapper {

    private final StorageService storage;
    private final JwtTokenProvider jwtTokenProvider;

    public UserProfileDto map(UserProfile userProfile) {
        return UserProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(userProfile.getName())
                .sex(Sex.getByValue(userProfile.getSex()))
                .description(userProfile.getDescription())
                .avatar(storage.findAvatarAsByteArray(userProfile.getAvatar()))
                .tokens(jwtTokenProvider.getTokens(userProfile))
                .preferences(userProfile.getPreferences().stream().map(pref -> Sex.getByValue(pref.getSex())).collect(Collectors.toList()))
                .build();
    }
}
