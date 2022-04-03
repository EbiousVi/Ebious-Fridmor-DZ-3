package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.Dto.FavouritesDto;
import ru.liga.Dto.SearchProfileDto;
import ru.liga.model.UserProfileData;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public void createUserProfile(UserProfileData userProfile) {
        HttpEntity<UserProfileData> request = new HttpEntity<>(userProfile);
        String url = "http://localhost:6064/dating-server/profiles/";
        ResponseEntity<Resource> resourceResponseEntity = restTemplate.postForEntity(url, request, Resource.class);
        if (!resourceResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("asdasdausdjasd");
        }
    }

    public List<SearchProfileDto> getSearchList(long chatId) {
        try {
            ResponseEntity<SearchProfileDto[]> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/search/for/" + chatId, SearchProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return List.of(resp.getBody());
            } else {
                throw new RuntimeException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<SearchProfileDto> getFavoriteList(long chatId) {
        try {
            ResponseEntity<SearchProfileDto[]> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/favourites/" + chatId, SearchProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return List.of(resp.getBody());
            } else {
                throw new RuntimeException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setFavoriteUser(Long fromChatId, Long toChatId) {
        try {
            HttpEntity<FavouritesDto> request = new HttpEntity<>(new FavouritesDto(fromChatId, toChatId));
            ResponseEntity<Void> resp = restTemplate.postForEntity("http://localhost:6064/dating-server/favourites/like", request, Void.class);
            if (!resp.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
