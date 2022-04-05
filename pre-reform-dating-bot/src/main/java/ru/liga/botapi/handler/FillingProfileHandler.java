package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.KeyboardName;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileGender;
import ru.liga.model.UserProfileState;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ReplyMessageService;
import ru.liga.service.RestTemplateService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FillingProfileHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final RestTemplateService restTemplateService;
    private final ReplyMessageService replyMessageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        UserProfileState profileState = profileData.getProfileState();

        switch (profileState) {
            case EMPTY_PROFILE:
                profileData.setChatId(chatId);
                userDataCache.setUserProfileData(userId, profileData);
                profileData.setProfileState(UserProfileState.SET_GENDER);

                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.fill.askGender"),
                        keyboardService.getReplyKeyboard(KeyboardName.GENDER_SELECT)));
            case SET_GENDER:
                if (Stream.of(UserProfileGender.values()).noneMatch(g -> g.getValue().equals(text))) {
                    return List.of(replyMessageService.getSendMessage(
                            chatId, localeMessageService.getMessage("reply.error.invalidValue"),
                            keyboardService.getReplyKeyboard(KeyboardName.GENDER_SELECT)));
                }

                profileData.setSex(UserProfileGender.getByValue(text));
                profileData.setProfileState(UserProfileState.SET_NAME);

                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.fill.askName"),
                        new ReplyKeyboardRemove(true, true)));
            case SET_NAME:
                profileData.setName(text);
                profileData.setProfileState(UserProfileState.SET_DESCRIPTION);

                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.fill.askDescription"),
                        new ReplyKeyboardRemove(true, true)));
            case SET_DESCRIPTION:
                profileData.setDescription(text);
                profileData.setProfileState(UserProfileState.SET_PREFERENCE);

                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.fill.askPreference"),
                        keyboardService.getReplyKeyboard(KeyboardName.PREFERENCE_SELECT)));
            case SET_PREFERENCE:
                if (Stream.of(UserProfileGender.values()).noneMatch(g -> g.getValue().equals(text)) &&
                        !text.equals(localeMessageService.getMessage("button.preference.all"))) {
                    return List.of(replyMessageService.getSendMessage(
                            chatId, localeMessageService.getMessage("reply.error.invalidValue"),
                            keyboardService.getReplyKeyboard(KeyboardName.PREFERENCE_SELECT)));
                }

                List<UserProfileGender> preferenceList;
                if (text.equals(localeMessageService.getMessage("button.preference.all"))) {
                    preferenceList = List.of(UserProfileGender.MALE, UserProfileGender.FEMALE);
                } else {
                    preferenceList = List.of(UserProfileGender.getByValue(text));
                }

                profileData.setPreferences(preferenceList);
                profileData.setProfileState(UserProfileState.COMPLETED_PROFILE);

                profileData.setAvatar(restTemplateService.createUserProfile(profileData).getAvatar());

                userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);

                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.main.info"),
                        keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU)));
            default:
                log.error("Filling profile error in {} class", FillingProfileHandler.class.getSimpleName());
                return List.of(replyMessageService.getSendMessage(
                        chatId, "Ошибка на стороне сервера: filling profile error", null));
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }
}
