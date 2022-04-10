package ru.liga.prereformdatingserver.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.UserProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.security.jwt.JwtTokenProvider;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileDtoMapper {

    private final JwtTokenProvider jwtTokenProvider;
    private final RestTranslatorService translatorService;
    private final RestAvatarService avatarService;

    public UserProfileDto map(UserProfile userProfile) {
        String description = translatorService.translate(userProfile.getDescription());
        return UserProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(translatorService.translate(userProfile.getName()))
                .sex(Sex.getByValue(userProfile.getSex()))
                .description(description)
                .avatar(avatarService.generateAvatar(description))
                .tokens(jwtTokenProvider.getTokens(userProfile))
                .preferences(userProfile.getPreferences().stream()
                        .map(pref -> Sex.getByValue(pref.getSex()))
                        .collect(Collectors.toList()))
                .build();
    }
}
