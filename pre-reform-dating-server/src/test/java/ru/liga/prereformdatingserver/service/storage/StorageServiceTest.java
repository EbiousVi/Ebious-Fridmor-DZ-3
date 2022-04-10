package ru.liga.prereformdatingserver.service.storage;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;

class StorageServiceTest extends PostgresContainer {

    @Autowired
    StorageService storageService;

    @Test
    void loadDefaultAvatar() {
        byte[] bytes = storageService.loadDefaultAvatar();
        Assertions.assertThat(bytes).isNotNull().hasSizeGreaterThan(1);
    }
}