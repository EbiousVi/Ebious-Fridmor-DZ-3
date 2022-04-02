package ru.liga.prereformdatingserver.service.dao.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prereformdatingserver.domain.dto.profileDto.CreateProfileDto;
import ru.liga.prereformdatingserver.domain.dto.profileDto.NewProfileDto;
import ru.liga.prereformdatingserver.domain.entity.Preferences;
import ru.liga.prereformdatingserver.domain.entity.UserProfile;
import ru.liga.prereformdatingserver.domain.enums.Sex;
import ru.liga.prereformdatingserver.service.dao.preferences.PreferencesService;
import ru.liga.prereformdatingserver.service.mapper.CreateProfileDtoMapper;
import ru.liga.prereformdatingserver.service.outer.avatar.RestAvatarService;
import ru.liga.prereformdatingserver.service.outer.translator.RestTranslatorService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserCreatorService {

    private final RestTranslatorService restTranslatorService;
    private final RestAvatarService restAvatarService;
    private final UserProfileService userProfileService;
    private final PreferencesService preferencesService;
    private final CreateProfileDtoMapper createProfileDtoMapper;

    @Autowired
    public UserCreatorService(RestTranslatorService restTranslatorService, RestAvatarService restAvatarService,
                              UserProfileService userProfileService, PreferencesService preferencesService,
                              CreateProfileDtoMapper createProfileDtoMapper) {
        this.restTranslatorService = restTranslatorService;
        this.restAvatarService = restAvatarService;
        this.userProfileService = userProfileService;
        this.preferencesService = preferencesService;
        this.createProfileDtoMapper = createProfileDtoMapper;
    }

    @Transactional
    public CreateProfileDto createProfile(NewProfileDto newProfileDto) {
        UserProfile userProfile = userProfileService.createUserProfile(newProfileDto);
        preferencesService.findPreferencesByChatId(newProfileDto);
        return createProfileDtoMapper.map(userProfile);
    }

    public NewProfileDto getUser(Long chatId) {
        UserProfile userProfileByChatId = userProfileService.getUserProfileByChatId(chatId);
        List<Preferences> preferences = preferencesService.findPreferencesByChatId(chatId);
        NewProfileDto newProfileDto = new NewProfileDto();
        newProfileDto.setChatId(userProfileByChatId.getChatId());
        newProfileDto.setName(userProfileByChatId.getName());
        newProfileDto.setSex(Sex.getByValue(userProfileByChatId.getSex()));
        newProfileDto.setDescription(userProfileByChatId.getDescription());
        newProfileDto.setPreferences(preferences.stream().map(pref -> Sex.getByValue(pref.getSex())).collect(Collectors.toList()));
        return newProfileDto;
    }
}
