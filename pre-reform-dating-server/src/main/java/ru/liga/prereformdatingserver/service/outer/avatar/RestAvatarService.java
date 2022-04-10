package ru.liga.prereformdatingserver.service.outer.avatar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prereformdatingserver.service.storage.StorageService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestAvatarService {

    private static final String AVATAR_PATH = "/api/avatar";
    private final RestTemplate restTemplate;
    private final StorageService storage;
    @Value("${outer-service.avatar}")
    private String avatarURL;

    public byte[] generateAvatar(String text) {
        try {
            HttpEntity<String> request = new HttpEntity<>(text);
            ResponseEntity<byte[]> response = restTemplate.postForEntity(avatarURL + AVATAR_PATH, request, byte[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Pre reform avatar service send bad response = {}", response);
                return storage.loadDefaultAvatar();
            }
        } catch (RestClientException e) {
            log.error("Pre reform avatar service unavailable now!", e);
            return storage.loadDefaultAvatar();
        }
    }
}
