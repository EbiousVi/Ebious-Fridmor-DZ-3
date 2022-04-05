package ru.liga.prereformdatingserver.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.security.JwtTokenProvider;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
@AllArgsConstructor
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
                .token(jwtTokenProvider.getTokens(userProfile))
                .build();
    }
}
