package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.Dto.ProfileDto;
import ru.liga.cache.UserDataCache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileImageService {
    private final UserDataCache userDataCache;

    private static final Path PATH = Paths.get("pre-reform-dating-bot", "profile_image.jpg");

    public InputFile getProfileImageForUser(long userId) {
        File profileImage = new File(String.valueOf(PATH));
        try {
            FileUtils.writeByteArrayToFile(profileImage, userDataCache.getUserProfileData(userId).getAvatar());
        } catch (IOException e) {
            log.error("class {} : file creation from byte array error", ProfileImageService.class.getSimpleName());
            return null;
        }
        return new InputFile(profileImage);
    }

    public InputFile getProfileImageForSuggestion(ProfileDto profileDto) {
        File profileImage = new File(String.valueOf(PATH));
        try {
            FileUtils.writeByteArrayToFile(profileImage, profileDto.getAvatar());
        } catch (IOException e) {
            log.error("class {} : file creation from byte array error", ProfileImageService.class.getSimpleName());
            return null;
        }
        return new InputFile(profileImage);
    }
}
