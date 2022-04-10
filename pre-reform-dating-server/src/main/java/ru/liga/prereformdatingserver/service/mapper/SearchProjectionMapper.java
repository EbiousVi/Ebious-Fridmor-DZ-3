package ru.liga.prereformdatingserver.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

@Service
@RequiredArgsConstructor
public class SearchProjectionMapper {

    private final RestTranslatorService translatorService;
    private final RestAvatarService avatarService;

    public SuggestionProfileDto map(SearchProfileProjection profile) {
        String description = translatorService.translate(profile.getDescription());
        return SuggestionProfileDto.builder()
                .chatId(profile.getChatId())
                .name(translatorService.translate(profile.getName()))
                .sex(profile.getSex())
                .avatar(avatarService.generateAvatar(description))
                .isMatch(profile.getIsPotentialMatch())
                .build();
    }
}
