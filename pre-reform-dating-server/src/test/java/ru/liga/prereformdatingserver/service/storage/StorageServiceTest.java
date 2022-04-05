package ru.liga.prereformdatingserver.service.storage;

import org.junit.Before;
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

    @Before
    public void setUp() {
//        ReflectionTestUtils.setField(storage, "storage", temp);
    }

    @Test
    void avatarToByteArray() {
        byte[] bytes = storage.findAvatarAsByteArray("1.jpg");
        assertThat(bytes).hasSizeGreaterThan(1);
    }

    @Test
    void save() {
        Path save = storage.saveAvatar(new byte[]{1, 0, 1});
        assertThat(save).exists();
    }
}