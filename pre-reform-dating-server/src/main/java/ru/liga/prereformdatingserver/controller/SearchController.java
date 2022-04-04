package ru.liga.prereformdatingserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.search.SearchService;

import java.util.List;

@RestController
@RequestMapping("/dating-server")
@AllArgsConstructor
public class SearchController {

    private final SearchService search;

    @GetMapping("/search/for/{chat-id}")
    public List<ProfileDto> search(@PathVariable("chat-id") Long chatId) {
        return search.searchProfiles(chatId);
    }
}
