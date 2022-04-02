package ru.liga.prereformdatingserver.service.dao.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.exception.UserProfileException;
import ru.liga.prereformdatingserver.service.dao.repository.UserProfileRepository;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserProfileByChatId(Long chatId) {
        return userProfileRepository.findByChatId(chatId)
                .orElseThrow(() -> new UserProfileException("User profile not found by chatId = " + chatId));
    }

    public UserProfile createUserProfile(NewProfileDto dto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setChatId(dto.getChatId());
        userProfile.setName(dto.getName());
        userProfile.setDescription(dto.getDescription());
        userProfile.setSex(dto.getSex().name);
        userProfile.setAvatar("1.jpg");
        userProfile.setIsNew(true);
        try {
            return userProfileRepository.save(userProfile);
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                throw new RuntimeException(e.getCause().getCause().getMessage());
            }
            e.printStackTrace();
        }
        throw new RuntimeException("Can not save user profile!");
    }

    public UserProfile updateUserProfile(NewProfileDto dto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setChatId(dto.getChatId());
        userProfile.setName(dto.getName());
        userProfile.setDescription(dto.getDescription());
        userProfile.setSex(dto.getSex().name);
        userProfile.setIsNew(false);
        userProfile.setAvatar("1.jpg");
        try {
            return userProfileRepository.save(userProfile);
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException) {
                throw new UserProfileException(e.getCause().getCause().getMessage());
            }
            e.printStackTrace();
        }
        throw new UserProfileException("Can not save user profile!");
    }
}
