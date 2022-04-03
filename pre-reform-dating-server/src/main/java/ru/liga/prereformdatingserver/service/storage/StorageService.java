package ru.liga.prereformdatingserver.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.prereformdatingserver.exception.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class StorageService {

    private static final Path AVATAR_STORAGE = Paths.get("src", "main", "resources", "storage", "avatar");
    private static final String IMAGE_EXTENSION = ".jpg";

    public Path save(MultipartFile file) {
        String filename = UUID.randomUUID() + IMAGE_EXTENSION;
        Path save = AVATAR_STORAGE.resolve(filename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, save, StandardCopyOption.REPLACE_EXISTING);
            return save;
        } catch (IOException e) {
            log.error("Can not save avatar image", e);
            throw new StorageException("Can not save avatar image!");
        }
    }

    public byte[] findAvatarAsByteArray(String filename) {
        String path = "/storage/avatar/" + filename;
        InputStream inputStream = this.getClass().getResourceAsStream(path);
        try {
            if (inputStream == null) return new byte[1];//throw new StorageException("Can not find resource!");
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error("Can not write avatar to byte array!", e);
            throw new StorageException("Can not write avatar to byte array!");
        }
    }
}
