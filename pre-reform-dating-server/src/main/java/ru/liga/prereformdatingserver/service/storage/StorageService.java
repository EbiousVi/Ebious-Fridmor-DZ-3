package ru.liga.prereformdatingserver.service.storage;

import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {
    /*File file = new ClassPathResource("countries.xml").getFile();*/

    private static final Path AVATAR_STORAGE = Paths.get("src", "main", "resources", "storage", "avatar");
    private static final String IMAGE_EXTENSION = ".jpg";

    public Path save(MultipartFile file) {
        String filename = UUID.randomUUID() + IMAGE_EXTENSION;
        Path save = AVATAR_STORAGE.resolve(filename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, save, StandardCopyOption.REPLACE_EXISTING);
            return save;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can not save from RestAvatarService");
    }
    //Как лучше сделать?
    public byte[] avatarToByteArray(String filename) {
        String path = "/storage/avatar/" + filename;
        InputStream inputStream = this.getClass().getResourceAsStream(path);
        try {
            if (inputStream == null) {
                return new byte[1];
            }
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can not write Avatar to byte array!");
        }
    }
}
