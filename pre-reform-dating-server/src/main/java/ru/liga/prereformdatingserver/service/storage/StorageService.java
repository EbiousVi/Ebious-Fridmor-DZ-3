package ru.liga.prereformdatingserver.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.exception.AvatarNotFoundException;

import java.io.IOException;

@Service
@Slf4j
public class StorageService {

    @Value("classpath:/static/default-avatar.jpg")
    private Resource defaultAvatar;

    public byte[] loadDefaultAvatar() {
        try {
            return IOUtils.toByteArray(defaultAvatar.getURI());
        } catch (IOException e) {
            log.error("Can not write default Avatar to byte array!", e);
            throw new AvatarNotFoundException("Avatars service does not work!");
        }
    }
}
