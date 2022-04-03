package ru.liga.prereformdatingserver.service.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.exception.UserProfileException;
import ru.liga.prereformdatingserver.service.repository.UserProfileRepository;

import java.util.stream.Collectors;

@Service
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserProfileByChatId(Long chatId) {
        return userProfileRepository.findById(chatId)
                .orElseThrow(() -> new UserProfileException("User profile not found by chatId = " + chatId));
    }

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
            throw new UserProfileException("Can not save user profile!");
        }
    }

    public UserProfile updateUserProfile(Long chatId, NewProfileDto dto) {
        UserProfile profile = UserProfile.builder()
                .chatId(chatId)
                .name(dto.getName())
                .sex(dto.getSex().name)
                .description(dto.getDescription())
                .avatar(dto.getAvatar().getFileName().toString())
                .isNew(false)
                .preferences(dto.getPreferences().stream()
                        .map(pref -> new Preferences(dto.getChatId(), pref.name))
                        .collect(Collectors.toSet()))
                .build();
        return userProfileRepository.save(profile);
    }

    public void deleteProfile(Long chatId) {
        userProfileRepository.deleteById(chatId);
    }
}