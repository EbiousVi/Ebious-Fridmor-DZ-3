package ru.liga.prereformdatingserver.service.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformdatingserver.domain.dto.profile.resp.ProfileDto;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.repository.SearchProfileRepository;
import ru.liga.prereformdatingserver.service.mapper.SearchProjectionMapper;
import ru.liga.prereformdatingserver.service.profile.UserProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchProfileRepository searchProfileRepository;
    private final SearchProjectionMapper projectionMapper;
    private final UserProfileService userProfileService;

    public List<ProfileDto> searchProfiles(Long chatId) {
        UserProfile userProfile = userProfileService.getUserProfileById(chatId);
        return searchProfileRepository.searchProfiles(userProfile.getChatId(), userProfile.getSex()).stream()
                .map(projectionMapper::map)
                .collect(Collectors.toList());
    }
}
