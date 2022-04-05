package ru.liga.prereformdatingserver.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class StorageService {

    private static final Path AVATAR_STORAGE = Paths.get("src", "main", "resources", "storage", "avatar");
    private static final String IMAGE_EXTENSION = ".jpg";

    public Path saveAvatar(byte[] bytes) {
        try {
            String filename = UUID.randomUUID() + IMAGE_EXTENSION;
            Path save = AVATAR_STORAGE.resolve(filename);
            FileUtils.writeByteArrayToFile(new File(save.toString()), bytes);
            return save;
        } catch (IOException e) {
            log.error("Can not save avatar image", e);
            throw new StorageException("Can not save avatar image!");
        }
    }

    public byte[] findAvatarAsByteArray(String filename) {
        try {
            return IOUtils.toByteArray(AVATAR_STORAGE.resolve(filename).toUri());
        } catch (IOException e) {
            log.error("Can not write avatar to byte array!", e);
            throw new StorageException("Can not write avatar to byte array!");
        }
    }
}
