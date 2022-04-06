package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.Dto.ProfileDto;
import ru.liga.Dto.UserProfileDto;
import ru.liga.model.UserProfileData;

import java.util.Collections;
import java.util.LinkedList;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    private final RestTemplate restTemplate;

    public UserProfileDto createUserProfile(UserProfileData userProfile) {
        HttpEntity<UserProfileData> request = new HttpEntity<>(userProfile);
        String url = "http://localhost:6064/dating-server/profiles";
        ResponseEntity<UserProfileDto> resp = restTemplate.postForEntity(url, request, UserProfileDto.class);
        if (resp.getStatusCode().is2xxSuccessful()) {
            return resp.getBody();
        } else {
            throw new RuntimeException("Create user profile request return bad response!");
        }
    }

    public UserProfileDto getUserProfile(UserProfileData userProfileData) {
        String url = "http://localhost:6064/dating-server/profile";
        try {
            ResponseEntity<UserProfileDto> usersResponse = restTemplate.exchange(url, HttpMethod.GET, getAuthorizationHeader(userProfileData), UserProfileDto.class);
            if (usersResponse.getStatusCode().is2xxSuccessful()) {
                return usersResponse.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            log.error("server is not available");
            return null;
        }
    }

    public LinkedList<ProfileDto> getSearchList(UserProfileData userProfileData) {
        String url = "http://localhost:6064/dating-server/search";
        try {
            ResponseEntity<ProfileDto[]> resp = restTemplate.exchange(url, HttpMethod.GET, getAuthorizationHeader(userProfileData), ProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                LinkedList<ProfileDto> linkedList = new LinkedList<>();
                Collections.addAll(linkedList, resp.getBody());
                return linkedList;
            } else {
                throw new RuntimeException("Get search list request return bad response!");
            }
        } catch (RestClientException e) {
            log.error("server is not available");
            return null;
        }
    }

    public LinkedList<ProfileDto> getFavoriteList(UserProfileData userProfileData) {
        String url = "http://localhost:6064/dating-server/favourites";
        try {
            ResponseEntity<ProfileDto[]> resp = restTemplate.exchange(url, HttpMethod.GET, getAuthorizationHeader(userProfileData), ProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                LinkedList<ProfileDto> linkedList = new LinkedList<>();
                Collections.addAll(linkedList, resp.getBody());
                return linkedList;
            } else {
                throw new RuntimeException("Get favorites list request return bad response!");
            }
        } catch (RestClientException e) {
            log.error("server is not available");
            return null;
        }
    }

    public void setFavoriteUser(UserProfileData userProfileData, Long toChatId) {
        String url = "http://localhost:6064/dating-server/favourites/" + toChatId;
        try {
            ResponseEntity<ProfileDto[]> resp = restTemplate.exchange(url, HttpMethod.GET, getAuthorizationHeader(userProfileData), ProfileDto[].class);
            if (!resp.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Set favorite request return bad response!");
            }
        } catch (RestClientException e) {
            log.error("server is not available");
        }
    }

    public HttpEntity<Void> getAuthorizationHeader(UserProfileData userProfileData) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, TOKEN_PREFIX + userProfileData.getTokens().getOrDefault("accessToken", ""));
        return new HttpEntity<>(headers);
    }
}
