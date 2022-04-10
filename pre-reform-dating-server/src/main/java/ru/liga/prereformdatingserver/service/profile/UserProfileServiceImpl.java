package ru.liga.prereformdatingserver.service.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
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
public class UserProfileServiceImpl implements UserProfileServiceI {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${credentials.pass}")
    private String password;

    @Override
    public UserProfile getUserProfileById(Long chatId) {
        return userProfileRepository.findById(chatId)
                .orElseThrow(() -> new UserProfileException("User profile not found by chatId = " + chatId));
    }

    @Override
    public UserProfile saveUserProfile(NewProfileDto dto) {
        UserProfile profile = UserProfile.builder()
                .chatId(dto.getChatId())
                .password(passwordEncoder.encode(password))
                .name(dto.getName())
                .sex(dto.getSex().name)
                .description(dto.getDescription())
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
                throw new UserProfileException("User profile = " + dto.getChatId() + " already register!", HttpStatus.NOT_FOUND);
            }
            if (e.getCause() instanceof DataIntegrityViolationException) {
                log.error("Invalid dto data =  {}", dto, e);
                throw new UserProfileException("Invalid User profile data!", HttpStatus.BAD_REQUEST);
            }
            log.error("Can not save user profile = {}", dto.getChatId(), e);
            throw new UserProfileException("Can not save user profile!");
        }
    }

    @Override
    public UserProfile updateUserProfile(Long chatId, NewProfileDto dto) {
        UserProfile userProfileById = getUserProfileById(chatId);
        userProfileById.setName(dto.getName());
        userProfileById.setSex(dto.getSex().name);
        userProfileById.setDescription(dto.getDescription());
        userProfileById.setPreferences(dto.getPreferences().stream()
                .map(pref -> new Preferences(chatId, pref.name))
                .collect(Collectors.toSet()));
        return userProfileRepository.save(userProfileById);
    }

    @Override
    public void deleteUserProfile(Long chatId) {
        userProfileRepository.deleteById(chatId);
    }
}