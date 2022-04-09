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
import ru.liga.service.OpenCsvService;
import ru.liga.service.ReplyMessageService;
import ru.liga.service.RestTemplateService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FillingProfileHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final RestTemplateService restTemplateService;
    private final ReplyMessageService replyMessageService;
    private final KeyboardService keyboardService;
    private final OpenCsvService openCsvService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        UserProfileState userProfileState = userProfileData.getProfileState();

        switch (userProfileState) {
            case EMPTY_PROFILE:
                userProfileData.setChatId(chatId);
                userDataCache.setUserProfileData(userId, userProfileData);
                userProfileData.setProfileState(UserProfileState.SET_GENDER);
                return sendMessage(chatId, "reply.fill.askGender", Keyboard.GENDER_SELECT);
            case SET_GENDER:
                if (!genderIsValid(text)) {
                    return sendMessage(chatId, "reply.error.invalidValue", Keyboard.GENDER_SELECT);
                }
                userProfileData.setSex(UserProfileGender.getByValue(text));
                userProfileData.setProfileState(UserProfileState.SET_NAME);
                return sendMessage(chatId, "reply.fill.askName", Keyboard.REMOVE);
            case SET_NAME:
                userProfileData.setName(text);
                userProfileData.setProfileState(UserProfileState.SET_DESCRIPTION);
                return sendMessage(chatId, "reply.fill.askDescription", Keyboard.REMOVE);
            case SET_DESCRIPTION:
                userProfileData.setDescription(text);
                userProfileData.setProfileState(UserProfileState.SET_PREFERENCE);
                return sendMessage(chatId, "reply.fill.askPreference", Keyboard.PREFERENCE_SELECT);
            case SET_PREFERENCE:
                if (!preferenceIsValid(text)) {
                    return sendMessage(chatId, "reply.error.invalidValue", Keyboard.PREFERENCE_SELECT);
                }
                List<UserProfileGender> preferenceList = text.equals(Button.ALL.getValue()) ?
                        List.of(UserProfileGender.MALE, UserProfileGender.FEMALE) :
                        List.of(UserProfileGender.getByValue(text));
                userProfileData.setPreferences(preferenceList);
                try {
                    processUserProfileData(userProfileData);
                } catch (IOException e) {
                    return sendError(chatId);
                }
                userProfileData.setProfileState(UserProfileState.COMPLETED_PROFILE);
                userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
                return sendMessage(chatId, "reply.main.info", Keyboard.MAIN_MENU);
            default:
                log.error("Filling profile error in {} class", FillingProfileHandler.class.getSimpleName());
                return sendError(chatId);

        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private void processUserProfileData(UserProfileData userProfileData) throws IOException {
        UserProfileDto userProfileDto = restTemplateService.createUserProfile(userProfileData);
        openCsvService.writeData(
                userProfileDto.getChatId(),
                userProfileDto.getTokens().get("accessToken"),
                userProfileDto.getTokens().get("refreshToken"));
        userProfileData.setChatId(userProfileDto.getChatId());
        userProfileData.setName(userProfileDto.getName());
        userProfileData.setSex(userProfileDto.getSex());
        userProfileData.setDescription(userProfileDto.getDescription());
        userProfileData.setAvatar(userProfileDto.getAvatar());
        userProfileData.setPreferences(userProfileDto.getPreferences());
        userProfileData.setTokens(userProfileDto.getTokens());
    }

    private List<PartialBotApiMethod<?>> sendMessage(long chatId, String message, Keyboard keyboardName) {
        return List.of(replyMessageService.getSendMessage(
                chatId, localeMessageService.getMessage(message), keyboardService.getReplyKeyboard(keyboardName)));
    }

    private List<PartialBotApiMethod<?>> sendError(long chatId) {
        return List.of(replyMessageService.getSendMessage(
                chatId, "Ошибка на стороне сервера: filling profile error", null));
    }

    private boolean genderIsValid(String text) {
        return Stream.of(UserProfileGender.values()).anyMatch(g -> g.getValue().equals(text));
    }

    private boolean preferenceIsValid(String text) {
        return genderIsValid(text) || text.equals(Button.ALL.getValue());
    }
}
