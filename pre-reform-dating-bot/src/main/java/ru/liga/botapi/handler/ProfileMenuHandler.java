package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.dto.UserProfileDto;
import ru.liga.keyboard.Button;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileGender;
import ru.liga.model.UserProfileState;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ProfileImageService;
import ru.liga.service.ReplyMessageService;
import ru.liga.service.RestTemplateService;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileMenuHandler implements UserInputHandler {
    private final RestTemplateService restTemplateService;
    private final ReplyMessageService replyMessageService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        UserProfileState userProfileState = userProfileData.getProfileState();

        switch (userProfileState) {
            case COMPLETED_PROFILE:
                if (text.equals(Button.PROFILE.getValue())) {
                    return replyMessageService.sendUserPhoto(chatId, userProfileData, Keyboard.PROFILE_MENU);
                }
                if (text.equals(Button.CHANGE_NAME.getValue())) {
                    userProfileData.setProfileState(UserProfileState.SET_NAME);
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.update.name", Keyboard.REMOVE);
                }
                if (text.equals(Button.CHANGE_SEX.getValue())) {
                    userProfileData.setProfileState(UserProfileState.SET_GENDER);
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.update.sex", Keyboard.GENDER_SELECT);
                }
                if (text.equals(Button.CHANGE_DESCRIPTION.getValue())) {
                    userProfileData.setProfileState(UserProfileState.SET_DESCRIPTION);
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.update.description", Keyboard.REMOVE);
                }
                if (text.equals(Button.CHANGE_PREFERENCE.getValue())) {
                    userProfileData.setProfileState(UserProfileState.SET_PREFERENCE);
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.update.preference", Keyboard.PREFERENCE_SELECT);
                }
                if (text.equals(Button.MAIN.getValue())) {
                    userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.main.info", Keyboard.MAIN_MENU);
                }
                return replyMessageService.sendPredeterminedMessage(
                        chatId, "reply.error.invalidValue", Keyboard.PROFILE_MENU);
            case SET_NAME:
                userProfileData.setName(text);
                processUserProfileData(userProfileData);
                return replyMessageService.sendCustomMessage(chatId, "Имя обновлено", Keyboard.PROFILE_MENU);
            case SET_GENDER:
                if (!genderIsValid(text)) {
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.error.invalidValue", Keyboard.GENDER_SELECT);
                }
                userProfileData.setSex(UserProfileGender.getByValue(text));
                processUserProfileData(userProfileData);
                return replyMessageService.sendCustomMessage(chatId, "Пол обновлен", Keyboard.PROFILE_MENU);
            case SET_DESCRIPTION:
                userProfileData.setDescription(text);
                processUserProfileData(userProfileData);
                return replyMessageService.sendCustomMessage(chatId, "Описание обновлено", Keyboard.PROFILE_MENU);
            case SET_PREFERENCE:
                if (!preferenceIsValid(text)) {
                    return replyMessageService.sendPredeterminedMessage(
                            chatId, "reply.error.invalidValue", Keyboard.PREFERENCE_SELECT);
                }
                List<UserProfileGender> preferenceList = text.equals(Button.ALL.getValue()) ?
                        List.of(UserProfileGender.MALE, UserProfileGender.FEMALE) :
                        List.of(UserProfileGender.getByValue(text));
                userProfileData.setPreferences(preferenceList);
                processUserProfileData(userProfileData);
                return replyMessageService.sendCustomMessage(
                        chatId, "Предпочтения обновлены", Keyboard.PROFILE_MENU);
            default:
                log.error("Updating profile error in {} class", ProfileMenuHandler.class.getSimpleName());
                return replyMessageService.sendCustomMessage(
                        chatId, "Ошибка на стороне сервера: updating profile error", Keyboard.PROFILE_MENU);
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PROFILE_MENU;
    }

    private void processUserProfileData(UserProfileData userProfileData) {
        UserProfileDto userProfileDto = restTemplateService.updateUserProfile(userProfileData);
        userProfileData.setChatId(userProfileDto.getChatId());
        userProfileData.setName(userProfileDto.getName());
        userProfileData.setSex(userProfileDto.getSex());
        userProfileData.setDescription(userProfileDto.getDescription());
        userProfileData.setAvatar(userProfileDto.getAvatar());
        userProfileData.setPreferences(userProfileDto.getPreferences());
        userProfileData.setTokens(userProfileDto.getTokens());
        userProfileData.setProfileState(UserProfileState.COMPLETED_PROFILE);
    }

    private boolean genderIsValid(String text) {
        return Stream.of(UserProfileGender.values()).anyMatch(g -> g.getValue().equals(text));
    }

    private boolean preferenceIsValid(String text) {
        return genderIsValid(text) || text.equals(Button.ALL.getValue());
    }
}
