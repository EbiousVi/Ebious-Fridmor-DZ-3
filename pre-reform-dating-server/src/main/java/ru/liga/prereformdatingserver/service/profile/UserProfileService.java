package ru.liga.prereformdatingserver.service.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.exception.UserProfileException;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService implements UserProfileServiceI {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile getAuthUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUserProfileByChatId(Long.parseLong(auth.getName()));
    }

    @Override
    public UserProfile getUserProfileByChatId(Long chatId) {
        return userProfileRepository.findById(chatId)
                .orElseThrow(() -> new UserProfileException("User profile not found by chatId = " + chatId));
    }

    @Override
    public UserProfile createUserProfile(NewProfileDto dto) {
        UserProfile profile = UserProfile.builder()
                .chatId(dto.getChatId())
                .name(dto.getName())
                .sex(dto.getSex().name)
                .description(dto.getDescription())
                .avatar(dto.getAvatar().getFileName().toString())
                .isNew(true)
                .preferences(dto.getPreferences().stream()
                        .map(pref -> new Preferences(dto.getChatId(), pref.name))
                        .collect(Collectors.toSet()))
                .build();
        try {
            return userProfileRepository.save(profile);
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                log.warn("User profile = {} already register!", dto.getChatId(), e);
                throw new UserProfileException("User profile = " + dto.getChatId() + " already register!");
            }
            log.error("Can not save user profile = {}", dto.getChatId(), e);
            e.printStackTrace();
            throw new UserProfileException("Can not save user profile!");
        }
    }

    @Override
    public UserProfile updateUserProfile(NewProfileDto dto) {
        UserProfile authUserProfile = getAuthUserProfile();
        UserProfile update = UserProfile.builder()
                .chatId(authUserProfile.getChatId())
                .name(dto.getName())
                .sex(dto.getSex().name)
                .description(dto.getDescription())
                .avatar(dto.getAvatar().getFileName().toString())
                .isNew(false)
                .preferences(dto.getPreferences().stream()
                        .map(pref -> new Preferences(dto.getChatId(), pref.name))
                        .collect(Collectors.toSet()))
                .build();
        return userProfileRepository.save(update);
    }

    @Override
    public void deleteUserProfile() {
        UserProfile authUserProfile = getAuthUserProfile();
        userProfileRepository.deleteById(authUserProfile.getChatId());
    }
}