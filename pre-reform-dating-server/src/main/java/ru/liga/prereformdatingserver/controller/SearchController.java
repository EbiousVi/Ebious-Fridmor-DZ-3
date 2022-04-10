package ru.liga.prereformdatingserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.SuggestionProfileDto;
import ru.liga.prereformdatingserver.service.search.SearchService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dating-server")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public List<SuggestionProfileDto> getSearchProfiles(Principal principal) {
        return searchService.searchProfiles(Long.parseLong(principal.getName()));
    }
}
