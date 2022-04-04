package ru.liga.prereformdatingserver.service.outer.avatar;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestAvatarService {

    private final RestTemplate restTemplate;
    private final StorageService storage;
    @Value("${outer-service.translator}")
    private String avatarUrl;

    public Path createAvatar(String text) {
        System.out.println(text);
        String url = "http://localhost:6016/getImage/";
        try {
            HttpEntity<String> request = new HttpEntity<>(text);
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url + text, byte[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                try {
                    FileUtils.writeByteArrayToFile(new File("asd.jpg"), response.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Path.of("1.jpg");
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
