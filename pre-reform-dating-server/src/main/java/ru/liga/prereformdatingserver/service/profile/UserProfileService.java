package ru.liga.prereformdatingserver.service.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.req.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.exception.UserProfileException;
import ru.liga.prereformdatingserver.repository.UserProfileRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService implements UserProfileServiceI {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${credentials.user}")
    private String password;

    @Override
    public UserProfile getUserProfileById(Long chatId) {
        return userProfileRepository.findById(chatId)
                .orElseThrow(() -> new UserProfileException("User profile not found by chatId = " + chatId));
    }

    /**
     * Заставлять пользователя вводить логин/пароль в боте не юзер френдли!
     * Поэтому пока хард код пароля...
     */
    @Override
    public UserProfile createUserProfile(NewProfileDto dto) {
        UserProfile profile = UserProfile.builder()
                .chatId(dto.getChatId())
                .password(passwordEncoder.encode(password))
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
            if (e.getCause() instanceof DataIntegrityViolationException) {
                log.error("Invalid dto data =  {}", dto, e);
                throw new UserProfileException("Invalid User profile data!");
            }
            log.error("Can not save user profile = {}", dto.getChatId(), e);
            throw new UserProfileException("Can not save user profile!");
        }
    }

    @Override
    public UserProfile updateUserProfile(Long chatId, NewProfileDto dto) {
        UserProfile update = UserProfile.builder()
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
        return userProfileRepository.save(update);
    }

    @Override
    public void deleteUserProfile(Long chatId) {
        userProfileRepository.deleteById(chatId);
    }
}