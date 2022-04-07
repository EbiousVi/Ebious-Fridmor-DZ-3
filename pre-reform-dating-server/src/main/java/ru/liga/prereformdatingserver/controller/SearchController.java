package ru.liga.prereformdatingserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.service.search.SearchService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dating-server")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService search;

    @GetMapping("/search")
    public List<ProfileDto> getSearchProfiles(Principal principal) {
        return search.searchProfiles(Long.parseLong(principal.getName()));
    }
}
