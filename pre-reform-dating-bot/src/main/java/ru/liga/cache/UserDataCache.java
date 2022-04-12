package ru.liga.cache;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.liga.botapi.BotState;
import ru.liga.dto.UserProfileDto;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserSuggestionList;
import ru.liga.model.UserProfileState;
import ru.liga.service.OpenCsvService;
import ru.liga.service.RestTemplateService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDataCache {
    private final RestTemplateService restTemplateService;
    private final OpenCsvService openCsvService;

    private final Map<Long, BotState> userBotStateMap = new HashMap<>();
    private final Map<Long, UserProfileData> userProfileDataMap = new HashMap<>();
    private final Map<Long, UserSuggestionList> userSuggestionListMap = new HashMap<>();

    @SneakyThrows
    @PostConstruct
    private void postConstruct() {
/*        List<String[]> data = openCsvService.readData();
        for (int i = 1; i < data.size(); i++) {
            String[] userData = data.get(i);
            long userId = Long.parseLong(userData[0]);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", userData[1]);
            tokens.put("refreshToken", userData[2]);
            UserProfileData userProfileData = new UserProfileData();
            userProfileData.setChatId(userId);
            userProfileData.setTokens(tokens);
            userProfileDataMap.put(userId, userProfileData);
        }*/
    }

    public BotState getUserCurrentBotState(long userId) {
        BotState botState = userBotStateMap.get(userId);
        return botState != null ? botState : BotState.FILLING_PROFILE;
    }

    public void setUserCurrentBotState(long userId, BotState botState) {
        userBotStateMap.put(userId, botState);
    }

    public UserProfileData getUserProfileData(long userId) {
        UserProfileData userProfileData = userProfileDataMap.get(userId);
        return userProfileData != null ? userProfileData : new UserProfileData();
    }

    public void setUserProfileData(long userId, UserProfileData userProfileData) {
        userProfileDataMap.put(userId, userProfileData);
    }

    public UserSuggestionList getUserProfileList(long userId) {
        UserSuggestionList userProfileDataList = userSuggestionListMap.get(userId);
        return userProfileDataList != null ? userProfileDataList : new UserSuggestionList(new LinkedList<>());
    }

    public void setUserProfileList(long userId, UserSuggestionList userSuggestionList) {
        userSuggestionListMap.put(userId, userSuggestionList);
    }

    public void fillUserDataCacheForUser(long userId) {
        if (userProfileDataMap.get(userId) == null) {
            UserProfileDto userProfileDto = restTemplateService.getUserProfile(userId);
            if (userProfileDto != null) {
                userBotStateMap.put(userId, BotState.MAIN_MENU);
                userProfileDataMap.put(userId, UserProfileData.builder()
                        .chatId(userProfileDto.getChatId())
                        .name(userProfileDto.getName())
                        .sex(userProfileDto.getSex())
                        .description(userProfileDto.getDescription())
                        .avatar(userProfileDto.getAvatar())
                        .preferences(userProfileDto.getPreferences())
                        .profileState(UserProfileState.COMPLETED_PROFILE)
                        .tokens(userProfileDto.getTokens())
                        .build());
            } else {
                userProfileDataMap.put(userId, UserProfileData.builder()
                        .chatId(userId)
                        .profileState(UserProfileState.EMPTY_PROFILE)
                        .build());
            }
        }

    }
}
