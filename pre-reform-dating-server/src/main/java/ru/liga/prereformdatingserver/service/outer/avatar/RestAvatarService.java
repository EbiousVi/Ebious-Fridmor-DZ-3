package ru.liga.prereformdatingserver.service.outer.avatar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prereformdatingserver.exception.RestOuterServiceException;
import ru.liga.prereformdatingserver.service.outer.dto.Description;
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestAvatarService {

    private static final String AVATAR_PATH = "/avatar";
    private final RestTemplate restTemplate;
    private final StorageService storage;
    @Value("${outer-service.avatar}")
    private String avatarURL;

    public Path createAvatar(Description description) {
        try {
            HttpEntity<Description> request = new HttpEntity<>(description);
            ResponseEntity<byte[]> response = restTemplate.postForEntity(avatarURL + AVATAR_PATH, request, byte[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return storage.saveAvatar(response.getBody());
            } else {
                log.error("Pre reform avatar service bad response = {}", response);
                throw new RestOuterServiceException("Pre reform avatar service return bad response!");
            }
        } catch (RestClientException e) {
            log.error("Pre reform avatar service unavailable now!", e);
            throw new RestOuterServiceException("Pre reform avatar service unavailable now!");
        }
    }
}
