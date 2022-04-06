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
import ru.liga.prereformdatingserver.service.storage.StorageService;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestAvatarService {

    private final RestTemplate restTemplate;
    private final StorageService storage;

    @Value("${outer-service.avatar}")
    private String avatarURL;

    public Path createAvatar(Domain domain) {
        try {
            String apiPath = "/getImage/";
            HttpEntity<Domain> request = new HttpEntity<>(domain);
            ResponseEntity<byte[]> response = restTemplate.postForEntity(avatarURL + apiPath, request, byte[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return storage.saveAvatar(response.getBody());
            } else {
                log.info("Avatar service bad response = {}", response);
                throw new RestOuterServiceException("Avatar service return bad response!");
            }
        } catch (RestClientException e) {
            log.warn("Avatar service unavailable now!", e);
            throw new RestOuterServiceException("Avatar service!");
        }
    }
}
