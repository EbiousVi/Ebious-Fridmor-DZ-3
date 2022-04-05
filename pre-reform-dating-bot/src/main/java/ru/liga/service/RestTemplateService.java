package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.Dto.FavouritesDto;
import ru.liga.Dto.ProfileDto;
import ru.liga.Dto.UserProfileDto;
import ru.liga.model.UserProfileData;

import java.util.Collections;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public UserProfileDto createUserProfile(UserProfileData userProfile) {
        HttpEntity<UserProfileData> request = new HttpEntity<>(userProfile);
        String url = "http://localhost:6064/dating-server/profiles/";
        ResponseEntity<UserProfileDto> resp = restTemplate.postForEntity(url, request, UserProfileDto.class);
        if (resp.getStatusCode().is2xxSuccessful()) {
            return resp.getBody();
        } else {
            throw new RuntimeException("Create user profile request return bad response!");
        }
    }

    public UserProfileDto getUserProfile(long chatId) {
        try {
            ResponseEntity<UserProfileDto> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/profiles/" + chatId, UserProfileDto.class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return resp.getBody();
            } else {
                throw new RuntimeException("Get user profile request return bad response!");
            }
        } catch (RestClientException e) {
            return null;
        }
    }

    public LinkedList<ProfileDto> getSearchList(long chatId) {
        try {
            ResponseEntity<ProfileDto[]> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/search/for/" + chatId, ProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                LinkedList<ProfileDto> linkedList = new LinkedList<>();
                Collections.addAll(linkedList, resp.getBody());
                return linkedList;
            } else {
                throw new RuntimeException("Get search list request return bad response!");
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public LinkedList<ProfileDto> getFavoriteList(long chatId) {
        try {
            ResponseEntity<ProfileDto[]> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/favourites/" + chatId, ProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                LinkedList<ProfileDto> linkedList = new LinkedList<>();
                Collections.addAll(linkedList, resp.getBody());
                return linkedList;
            } else {
                throw new RuntimeException("Get favorites list request return bad response!");
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setFavoriteUser(Long fromChatId, Long toChatId) {
        try {
            HttpEntity<FavouritesDto> request = new HttpEntity<>(new FavouritesDto(fromChatId, toChatId));
            ResponseEntity<Void> resp = restTemplate.postForEntity("http://localhost:6064/dating-server/favourites/like", request, Void.class);
            if (!resp.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Set favorite request return bad response!");
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
