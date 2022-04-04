package ru.liga.prereformdatingserver.service.outer.translator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prereformdatingserver.exception.RestOuterServiceException;
import ru.liga.prereformdatingserver.service.outer.avatar.Domain;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestTranslatorService {

    private final RestTemplate restTemplate;

    @Value("${outer-service.translator}")
    private String translatorURL;

    public String translateToString(String text) {
        try {
            HttpEntity<String> request = new HttpEntity<>(text);
            ResponseEntity<String> response =
                    restTemplate.postForEntity(translatorURL + "/translate-string", request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.info("Pre reform translator bad response = {}", response);
                throw new RestOuterServiceException("Pre reform translator bad response!");
            }
        } catch (RestClientException e) {
            log.warn("Pre reform translator unavailable now!", e);
            throw new RestOuterServiceException("Pre reform translator unavailable now!");
        }
    }

    public Domain translateToObject(String text) {
        try {
            HttpEntity<String> request = new HttpEntity<>(text);
            ResponseEntity<Domain> response =
                    restTemplate.postForEntity(translatorURL + "/translate-object", request, Domain.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.info("Pre reform translator bad response = {}", response);
                throw new RestOuterServiceException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            log.warn("Pre reform translator unavailable now!", e);
            throw new RestOuterServiceException("Pre reform translator unavailable now!");
        }
    }
}
