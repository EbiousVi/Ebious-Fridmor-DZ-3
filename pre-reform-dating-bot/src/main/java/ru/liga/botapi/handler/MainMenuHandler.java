package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.dto.ProfileDto;
import ru.liga.keyboard.Button;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserSuggestionList;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ProfileImageService;
import ru.liga.service.ReplyMessageService;
import ru.liga.service.RestTemplateService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final RestTemplateService restTemplateService;
    private final ReplyMessageService replyMessageService;
    private final ProfileImageService profileImageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);

        if (text.equals(Button.SEARCH.getValue())) {
            UserSuggestionList userSuggestionList = new UserSuggestionList(
                    restTemplateService.getSearchList(userProfileData));
            if (userSuggestionList.isEmpty()) {
                return sendMessage(chatId, "reply.list.emptySuggestion", Keyboard.MAIN_MENU);
            }
            userDataCache.setUserProfileList(userId, userSuggestionList);
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
            ProfileDto currentSuggestion = userSuggestionList.getCurrent();
            return sendSuggestionPhoto(chatId, currentSuggestion, Keyboard.SEARCH_MENU);
        }

        if (text.equals(Button.PROFILE.getValue())) {
            userDataCache.setUserCurrentBotState(userId, BotState.PROFILE_MENU);
            return sendUserPhoto(chatId, userProfileData, Keyboard.PROFILE_MENU);
        }

        if (text.equals(Button.FAVORITE.getValue())) {
            UserSuggestionList userSuggestionList = new UserSuggestionList(
                    restTemplateService.getFavoriteList(userProfileData));
            if (userSuggestionList.isEmpty()) {
                return sendMessage(chatId, "reply.list.emptyFavorites", Keyboard.MAIN_MENU);
            }
            userDataCache.setUserProfileList(userId, userSuggestionList);
            userDataCache.setUserCurrentBotState(userId, BotState.FAVORITE_MENU);
            ProfileDto currentSuggestion = userSuggestionList.getCurrent();
            return sendSuggestionPhoto(chatId, currentSuggestion, Keyboard.FAVORITE_MENU);
        }

        return sendMessage(chatId, "reply.error.invalidValue", Keyboard.MAIN_MENU);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN_MENU;
    }

    private List<PartialBotApiMethod<?>> sendMessage(long chatId, String message, Keyboard keyboardName) {
        return List.of(replyMessageService.getSendMessage(
                chatId, localeMessageService.getMessage(message), keyboardService.getReplyKeyboard(keyboardName)));
    }

    private List<PartialBotApiMethod<?>> sendSuggestionPhoto(long chatId, ProfileDto suggestion, Keyboard keyboardName) {
        String caption = suggestion.getName() + ", " + suggestion.getSex() + ", " + suggestion.getStatus();
        return List.of(replyMessageService.getSendPhoto(
                chatId, profileImageService.getProfileAvatarForSuggestion(suggestion),
                caption, keyboardService.getReplyKeyboard(keyboardName)));
    }

    private List<PartialBotApiMethod<?>> sendUserPhoto(long chatId, UserProfileData userProfileData, Keyboard keyboardName) {
        String caption = userProfileData.getName() + ", " + userProfileData.getSex().getValue();
        return List.of(replyMessageService.getSendPhoto(
                chatId, profileImageService.getProfileAvatarForUser(chatId),
                caption, keyboardService.getReplyKeyboard(keyboardName)));
    }
}
