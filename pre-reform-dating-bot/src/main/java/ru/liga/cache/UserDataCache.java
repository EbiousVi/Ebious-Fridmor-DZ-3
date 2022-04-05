package ru.liga.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.Dto.UserProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileList;
import ru.liga.model.UserProfileState;
import ru.liga.service.RestTemplateService;

import java.util.HashMap;
import java.util.LinkedList;
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
        if (userProfileDataMap.get(userId) == null) {
            UserProfileDto userProfileDto = null;//restTemplateService.getUserProfile(userProfileDataMap.get(userId));
            if (userProfileDto != null) {
                userBotStateMap.put(userId, BotState.MAIN_MENU);
                userProfileDataMap.put(userId, UserProfileData.builder()
                        .chatId(userProfileDto.getChatId())
                        .name(userProfileDto.getName())
                        .sex(userProfileDto.getSex())
                        .description(userProfileDto.getDescription())
                        .avatar(userProfileDto.getAvatar())
                        .profileState(UserProfileState.COMPLETED_PROFILE)
                        .build());
            }
        }
    }
}
