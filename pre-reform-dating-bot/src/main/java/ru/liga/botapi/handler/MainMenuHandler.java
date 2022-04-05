package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.ProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.KeyboardName;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileList;
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

        if (text.equals(localeMessageService.getMessage("button.main.search"))) {
            UserProfileList userProfileList = new UserProfileList(restTemplateService.getSearchList(chatId));

            if (userProfileList.isEmpty()) {
                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.list.emptySuggestion"), null));
            }

            userDataCache.setUserProfileList(userId, userProfileList);
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);

            ProfileDto currentSuggestion = userProfileList.getCurrent();
            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForSuggestion(currentSuggestion),
                    currentSuggestion.getName() + ", " + currentSuggestion.getSex(),
                    keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU)));
        } else if (text.equals(localeMessageService.getMessage("button.main.profile"))) {
            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForUser(userId),
                    userProfileData.getName() + ", " + userProfileData.getSex().getValue(), null));
        } else if (text.equals(localeMessageService.getMessage("button.main.favorite"))) {
            UserProfileList userProfileList = new UserProfileList(restTemplateService.getFavoriteList(chatId));

            if (userProfileList.isEmpty()) {
                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.list.emptyFavorites"), null));
            }

            userDataCache.setUserProfileList(userId, userProfileList);
            userDataCache.setUserCurrentBotState(userId, BotState.FAVORITE_MENU);

            ProfileDto currentSuggestion = userProfileList.getCurrent();

            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForSuggestion(currentSuggestion),
                    currentSuggestion.getName() + ", " + currentSuggestion.getSex(),
                    keyboardService.getReplyKeyboard(KeyboardName.FAVORITE_MENU)));
        } else {
            return List.of(replyMessageService.getSendMessage(
                    chatId, localeMessageService.getMessage("reply.error.invalidValue"),
                    keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU)));
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN_MENU;
    }
}
