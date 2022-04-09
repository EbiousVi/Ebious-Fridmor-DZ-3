package ru.liga.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.UserProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileList;
import ru.liga.model.UserProfileState;
import ru.liga.service.OpenCsvService;
import ru.liga.service.RestTemplateService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDataCache {
    private final Map<Long, BotState> userBotStateMap = new HashMap<>();
    private final Map<Long, UserProfileData> userProfileDataMap = new HashMap<>();
    private final Map<Long, UserProfileList> userProfileListMap = new HashMap<>();
    private final RestTemplateService restTemplateService;
    private final OpenCsvService openCsvService;

    @PostConstruct
    private void postConstruct() {
        List<String[]> data = openCsvService.readData();
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
        }
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

    public UserProfileList getUserProfileList(long userId) {
        UserProfileList userProfileDataList = userProfileListMap.get(userId);
        return userProfileDataList != null ? userProfileDataList : new UserProfileList(new LinkedList<>());
    }

    public void setUserProfileList(long userId, UserProfileList userProfileList) {
        userProfileListMap.put(userId, userProfileList);
    }

    public void fillUserDataCacheForUser(long userId) {
        if (userProfileDataMap.get(userId) != null) {
            if (userProfileDataMap.get(userId).getProfileState() == UserProfileState.EMPTY_PROFILE) {
                UserProfileDto userProfileDto = restTemplateService.getUserProfile(userProfileDataMap.get(userId));
                if (userProfileDto != null) {
                    userBotStateMap.put(userId, BotState.MAIN_MENU);
                    userProfileDataMap.put(userId, UserProfileData.builder()
                            .chatId(userProfileDto.getChatId())
                            .name(userProfileDto.getName())
                            .sex(userProfileDto.getSex())
                            .description(userProfileDto.getDescription())
                            .avatar(userProfileDto.getAvatar())
                            .profileState(UserProfileState.COMPLETED_PROFILE)
                            .tokens(userProfileDataMap.get(userId).getTokens())
                            .build());
                }
            }
        }
    }
}
