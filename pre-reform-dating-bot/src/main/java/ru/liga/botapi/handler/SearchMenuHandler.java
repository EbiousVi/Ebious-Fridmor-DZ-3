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
import ru.liga.model.UserSuggestionList;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ProfileImageService;
import ru.liga.service.ReplyMessageService;
import ru.liga.service.RestTemplateService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchMenuHandler implements UserInputHandler {
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

        UserSuggestionList userSuggestionList = userDataCache.getUserProfileList(userId);

        if (userSuggestionList.isEmpty()) {
            return sendMessage(chatId, "reply.list.suggestionIsOver", Keyboard.SEARCH_MENU);
        }

        if (text.equals(Button.LEFT.getValue())) {
            ProfileDto nextSuggestion = userSuggestionList.getNext();
            return sendSuggestionPhoto(chatId, nextSuggestion, Keyboard.SEARCH_MENU);
        }

        if (text.equals(Button.RIGHT.getValue())) {
            ProfileDto currentSuggestion = userSuggestionList.getCurrent();
            userSuggestionList.removeCurrent();
            restTemplateService.setFavoriteUser(
                    userDataCache.getUserProfileData(chatId), currentSuggestion.getChatId());
            if (currentSuggestion.getIsMatch()) {
                userDataCache.setUserCurrentBotState(userId, BotState.LIKE_MENU);
                return sendMessage(chatId, "reply.search.reciprocity", Keyboard.LIKE_MENU);
            }
            ProfileDto nextSuggestion = userSuggestionList.getNext();
            return sendSuggestionPhoto(chatId, nextSuggestion, Keyboard.SEARCH_MENU);
        }

        if (text.equals(Button.MAIN.getValue())) {
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
            return sendMessage(chatId, "reply.main.info", Keyboard.MAIN_MENU);
        }

        return sendMessage(chatId, "reply.error.invalidValue", Keyboard.SEARCH_MENU);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_MENU;
    }

    private List<PartialBotApiMethod<?>> sendMessage(long chatId, String message, Keyboard keyboardName) {
        return List.of(replyMessageService.getSendMessage(
                chatId, localeMessageService.getMessage(message), keyboardService.getReplyKeyboard(keyboardName)));
    }

    private List<PartialBotApiMethod<?>> sendSuggestionPhoto(long chatId, ProfileDto suggestion, Keyboard keyboardName) {
        String caption = suggestion.getName() + ", " + suggestion.getSex();
        return List.of(replyMessageService.getSendPhoto(
                chatId, profileImageService.getProfileAvatarForSuggestion(suggestion),
                caption, keyboardService.getReplyKeyboard(keyboardName)));
    }
}
