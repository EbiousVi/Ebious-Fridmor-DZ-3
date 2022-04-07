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
import ru.liga.prereformdatingserver.service.outer.dto.Description;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestTranslatorService {

    private final static String TRANSLATOR_TEXT_PATH = "/translator/text";
    private final static String TRANSLATOR_DESCRIPTION_PATH = "/translator/description";
    private final RestTemplate restTemplate;
    @Value("${outer-service.translator}")
    private String translatorURL;

    public Description translateDescription(String description) {
        try {
            HttpEntity<String> request = new HttpEntity<>(description);
            ResponseEntity<Description> response =
                    restTemplate.postForEntity(translatorURL + TRANSLATOR_DESCRIPTION_PATH, request, Description.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Pre reform translator bad response = {}", response);
                throw new RestOuterServiceException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            log.error("Pre reform translator unavailable now!", e);
            throw new RestOuterServiceException("Pre reform translator unavailable now!");
        }
    }
}
