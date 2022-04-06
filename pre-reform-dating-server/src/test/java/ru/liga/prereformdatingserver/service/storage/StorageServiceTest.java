package ru.liga.prereformdatingserver.service.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StorageServiceTest {

    @TempDir
    static Path temp;
    @Autowired
    StorageService storage;

    @Test
    void avatarToByteArray() {
        Path test = Path.of("src", "test", "resources", "avatar");
        ReflectionTestUtils.setField(storage, "AVATAR_STORAGE", test);
        byte[] bytes = storage.findAvatarAsByteArray("1.jpg");
        assertThat(bytes).hasSizeGreaterThan(1);
    }

    @Test
    void save() {
        ReflectionTestUtils.setField(storage, "AVATAR_STORAGE", temp);
        Path save = storage.saveAvatar(new byte[]{1, 0, 1});
        assertThat(save).exists();
    }
}