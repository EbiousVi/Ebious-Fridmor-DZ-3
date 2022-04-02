package ru.liga.prereformdatingserver.service.outer.translator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prereformdatingserver.exception.RestOuterServiceException;

@Service
@Slf4j
public class RestTranslatorService {

    private final RestTemplate restTemplate;

    @Value("${outer-service.translator}")
    private String translatorUrl;

    @Autowired
    public RestTranslatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translateIntoPreReformDialect(String text) {
        HttpEntity<String> request = new HttpEntity<>(text);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(translatorUrl, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.warn("");
                throw new RestOuterServiceException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            log.warn("");
            throw new RestOuterServiceException("Pre reform translator unavailable now!");
        }
    }
}
