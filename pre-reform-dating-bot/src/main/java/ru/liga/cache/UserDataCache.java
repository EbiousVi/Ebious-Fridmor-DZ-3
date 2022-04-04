package ru.liga.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.Dto.NewProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileList;
import ru.liga.service.RestTemplateService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDataCache {
    private final Map<Long, BotState> userBotStateMap = new HashMap<>();
    private final Map<Long, UserProfileData> userProfileDataMap = new HashMap<>();
    private final Map<Long, UserProfileList> userProfileListMap = new HashMap<>();
    private final RestTemplateService restTemplateService;

    public BotState getUserCurrentBotState(long userId) {
        BotState botState = userBotStateMap.get(userId);
        if (botState == null) {
            NewProfileDto newProfileDto = restTemplateService.getUserProfile(userId);
            if (newProfileDto == null) {
                userBotStateMap.put(userId, BotState.FILLING_PROFILE);
                return BotState.FILLING_PROFILE;
            } else {
                UserProfileData userProfileData = new UserProfileData();
                userProfileData.setChatId(newProfileDto.getChatId());
                userProfileData.setName(newProfileDto.getName());
                userProfileData.setSex(newProfileDto.getSex());
                userProfileData.setDescription(newProfileDto.getDescription());
                userProfileData.setPreferences(newProfileDto.getPreferences());
                userProfileDataMap.put(userId, userProfileData);
                return BotState.MAIN_MENU;
            }
        } else {
            return botState;
        }
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
        return userProfileDataList != null ? userProfileDataList : new UserProfileList(new ArrayList<>());
    }

    public void setUserProfileList(long userId, UserProfileList userProfileList) {
        userProfileListMap.put(userId, userProfileList);
    }
}
