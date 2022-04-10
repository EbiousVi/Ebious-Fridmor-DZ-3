package ru.liga.prereformdatingserver.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Relation;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

@Service
@RequiredArgsConstructor
public class FavouritesProfileDtoMapper {

    private final RestTranslatorService translatorService;
    private final RestAvatarService avatarService;

    public SuggestionProfileDto map(UserProfile userProfile, Relation relation) {
        return SuggestionProfileDto.builder()
                .chatId(userProfile.getChatId())
                .name(translatorService.translate(userProfile.getName()))
                .sex(userProfile.getSex())
                .status(relation.value)
                .isMatch(false)
                .avatar(avatarService.generateAvatar(userProfile.getDescription()))
                .build();
    }
}
