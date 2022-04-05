package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import ru.liga.Dto.ProfileDto;
import ru.liga.cache.UserDataCache;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileImageService {
    private final UserDataCache userDataCache;


    public File getProfileImageForUser(long userId) {
        File profileImage = new File("profile_image.jpg");
        try {
            FileUtils.writeByteArrayToFile(profileImage, userDataCache.getUserProfileData(userId).getAvatar());
        } catch (IOException e) {
            log.error("class {} : file creation from byte array error", ProfileImageService.class.getSimpleName());
            return null;
        }
        return profileImage;
    }

    public File getProfileImageForSuggestion(ProfileDto profileDto) {
        File profileImage = new File("profile_image.jpg");
        try {
            FileUtils.writeByteArrayToFile(profileImage, profileDto.getAvatar());
        } catch (IOException e) {
            log.error("class {} : file creation from byte array error", ProfileImageService.class.getSimpleName());
            return null;
        }
        return profileImage;
    }
}
