package ru.liga.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.liga.Dto.NewProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDataCache {
    private final Map<Long, BotState> userBotStateMap = new HashMap<>();
    private final Map<Long, UserProfileData> userProfileDataMap = new HashMap<>();
    private final Map<Long, UserProfileList> userProfileListMap = new HashMap<>();
    private final RestTemplate restTemplate;

    public BotState getUserCurrentBotState(long userId) {
        BotState botState = userBotStateMap.get(userId);

        if (botState == null) {
            NewProfileDto newProfileDto = restSearch(userId);
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

//        return botState != null ? botState: BotState.FILLING_PROFILE;
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

    public NewProfileDto restSearch(long chatId) {
        try {
            ResponseEntity<NewProfileDto> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/profiles/" + chatId, NewProfileDto.class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return resp.getBody();
            } else {
                throw new RuntimeException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
            return null;
        }
    }
}
