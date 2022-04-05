package ru.liga.prereformdatingserver.service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.prereformdatingserver.PostgresContainer;
import ru.liga.prereformdatingserver.domain.projection.SearchProfileProjection;

import java.util.List;

class UserProfileRepositoryTest extends PostgresContainer {

    @Autowired
    SearchProfileRepository searchProfileRepository;

    @Test
    void searchProfiles2() {
        List<SearchProfileProjection> iSearchProfiles = searchProfileRepository.searchProfiles(100L);
        iSearchProfiles.forEach(System.out::println);
    }
}