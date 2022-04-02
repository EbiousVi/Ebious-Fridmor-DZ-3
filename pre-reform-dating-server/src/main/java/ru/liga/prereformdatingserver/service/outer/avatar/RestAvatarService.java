package ru.liga.prereformdatingserver.service.outer.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RestAvatarService {

    private final RestTemplate restTemplate;
    private final StorageService storage;
    @Value("${outer-service.translator}")
    private String avatarUrl;

    @Autowired
    public RestAvatarService(RestTemplate restTemplate, StorageService storage) {
        this.restTemplate = restTemplate;
        this.storage = storage;
    }

    public Path createAvatar(String text) {
        HttpEntity<String> request = new HttpEntity<>(text);
        ResponseEntity<MultipartFile> response = restTemplate.postForEntity(avatarUrl, request, MultipartFile.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return storage.save(response.getBody());
        } else {
            throw new RuntimeException("RestAvatarService bad response");
        }
    }


}
