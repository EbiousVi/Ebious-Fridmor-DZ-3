package ru.liga.prereformdatingserver.service.outer.avatar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.prereformdatingserver.exception.RestOuterServiceException;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.nio.file.Path;

@Service
@Slf4j
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
        try {
            HttpEntity<String> request = new HttpEntity<>(text);
            ResponseEntity<MultipartFile> response = restTemplate.postForEntity(avatarUrl, request, MultipartFile.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return storage.save(response.getBody());
            } else {
                log.info("Avatar service return {}", response);
                throw new RestOuterServiceException("Avatar service return bad response!");
            }
        } catch (RestClientException e) {
            log.warn("Avatar service unavailable now!", e);
            throw new RestOuterServiceException("Avatar service!");
        }
    }
}
