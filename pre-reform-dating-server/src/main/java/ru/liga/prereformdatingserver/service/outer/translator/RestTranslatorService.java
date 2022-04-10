package ru.liga.prereformdatingserver.service.outer.translator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestTranslatorService {

    private final static String TRANSLATOR_PATH = "/api/translator/text";
    private final RestTemplate restTemplate;
    @Value("${outer-service.translator}")
    private String translatorURL;

    public String translate(String text) {
        try {
            HttpEntity<String> request = new HttpEntity<>(text);
            ResponseEntity<String> response = restTemplate.postForEntity(translatorURL + TRANSLATOR_PATH, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Pre reform translator send bad response = {}", response);
                return text;
            }
        } catch (RestClientException e) {
            log.error("Pre reform translator unavailable now!", e);
            return text;
        }
    }
}
