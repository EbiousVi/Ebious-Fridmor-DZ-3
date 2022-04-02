package ru.liga.prereformdatingserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.profileDto.SearchProfileDto;
import ru.liga.prereformdatingserver.service.dao.search.SearchService;

import java.util.List;

@RestController
@RequestMapping("/dating-server")
public class SearchController {

    private final SearchService search;

    @Autowired
    public SearchController(SearchService search) {
        this.search = search;
    }

    @GetMapping("/search/for/{chat-id}")
    public List<SearchProfileDto> search(@PathVariable("chat-id") Long chatId) {
        return search.searchProfiles(chatId);
    }
}
