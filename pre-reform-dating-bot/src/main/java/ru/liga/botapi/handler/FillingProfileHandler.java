package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileGender;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileState;
import ru.liga.service.LocaleMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FillingProfileHandler implements UserInputHandler {
    private final UserDataCache userDataCache;
    private final LocaleMessageService localeMessageService;
    private final KeyboardService keyboardService;
    private final RestTemplate restTemplate;

    @Override
    public SendMessage handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        UserProfileState profileState = profileData.getProfileState();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        switch (profileState) {
            case EMPTY_PROFILE:
                profileData.setChatId(chatId);
                userDataCache.setUserProfileData(userId, profileData);
                profileData.setProfileState(UserProfileState.SET_GENDER);
                sendMessage.setText(localeMessageService.getMessage("reply.fill.askGender"));
                sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.GENDER_SELECT));
                break;
            case SET_GENDER:
                if (Stream.of(UserProfileGender.values()).noneMatch(g -> g.getValue().equals(text))) {
                    sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
                    sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.GENDER_SELECT));
                    break;
                }
                profileData.setSex(UserProfileGender.getByValue(text));
                profileData.setProfileState(UserProfileState.SET_NAME);
                sendMessage.setText(localeMessageService.getMessage("reply.fill.askName"));
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true, true));
                break;
            case SET_NAME:
                profileData.setName(text);
                profileData.setProfileState(UserProfileState.SET_DESCRIPTION);
                sendMessage.setText(localeMessageService.getMessage("reply.fill.askDescription"));
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true, true));
                break;
            case SET_DESCRIPTION:
                profileData.setDescription(text);
                profileData.setProfileState(UserProfileState.SET_PREFERENCE);
                sendMessage.setText(localeMessageService.getMessage("reply.fill.askPreference"));
                sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.PREFERENCE_SELECT));
                break;
            case SET_PREFERENCE:
                if (Stream.of(UserProfileGender.values()).noneMatch(g -> g.getValue().equals(text)) &&
                        !text.equals(localeMessageService.getMessage("button.preference.all"))) {
                    sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
                    sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.PREFERENCE_SELECT));
                    break;
                }
                List<UserProfileGender> preferenceList = new ArrayList<>();
                if (text.equals(localeMessageService.getMessage("button.preference.all"))) {
                    preferenceList.add(UserProfileGender.MALE);
                    preferenceList.add(UserProfileGender.FEMALE);
                } else {
                    preferenceList.add(UserProfileGender.getByValue(text));
                }
                profileData.setPreferences(preferenceList);
                profileData.setProfileState(UserProfileState.COMPLETED_PROFILE);
                //Выводим на экран анкету пользователя
                rest(profileData);
                sendMessage.setText(profileData.toString());
                sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.MAIN_MENU));
                userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
                break;
            default:
                log.error("Filling profile error in {} class", FillingProfileHandler.class.getSimpleName());
                sendMessage.setText("Ошибка на стороне сервера: filling profile error");
        }
        return sendMessage;
    }

    public void rest(UserProfileData userProfile) {
        HttpEntity<UserProfileData> request = new HttpEntity<>(userProfile);
        String url = "http://localhost:6064/dating-server/profiles/";
        ResponseEntity<Resource> resourceResponseEntity = restTemplate.postForEntity(url, request, Resource.class);
        if (!resourceResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("asdasdausdjasd");
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }
}
