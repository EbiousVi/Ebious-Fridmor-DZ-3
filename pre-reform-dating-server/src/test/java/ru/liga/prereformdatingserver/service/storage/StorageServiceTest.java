package ru.liga.prereformdatingserver.service.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @InjectMocks
    StorageService storage;

    @TempDir
    static Path temp;
    @BeforeAll
    static void init() throws IOException {
        Files.createFile(temp.resolve("foo.jpg"));
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(storage, "AVATAR_STORAGE", temp);
    }

    @Test
    void avatarToByteArray() {
        byte[] bytes = storage.findAvatarAsByteArray("foo.jpg");
        assertThat(bytes).isNotNull();
    }

    @Test
    void save() {
        Path save = storage.saveAvatar(new byte[]{1, 0, 1});
        assertThat(save).exists();
    }
}