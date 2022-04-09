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

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final ReplyMessageService replyMessageService;
    private final ProfileImageService profileImageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.CONTINUE.getValue())) {
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
            UserSuggestionList userSuggestionList = userDataCache.getUserProfileList(userId);
            if (userSuggestionList.isEmpty()) {
                return sendMessage(chatId, "reply.list.suggestionIsOver", Keyboard.SEARCH_MENU);
            }
            ProfileDto nextSuggestion = userSuggestionList.getNext();
            return sendPhoto(chatId, nextSuggestion);
        }

        if (text.equals(Button.CHAT.getValue())) {
            return sendMessage(chatId, "reply.like.inDevelopment", Keyboard.LIKE_MENU);
        }

        return sendMessage(chatId, "reply.error.invalidValue", Keyboard.LIKE_MENU);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.LIKE_MENU;
    }

    private List<PartialBotApiMethod<?>> sendMessage(long chatId, String message, Keyboard keyboardName) {
        return List.of(replyMessageService.getSendMessage(
                chatId, localeMessageService.getMessage(message), keyboardService.getReplyKeyboard(keyboardName)));
    }

    private List<PartialBotApiMethod<?>> sendPhoto(long chatId, ProfileDto suggestion) {
        String caption = suggestion.getName() + ", " + suggestion.getSex() + ", " + suggestion.getStatus();
        return List.of(replyMessageService.getSendPhoto(
                chatId, profileImageService.getProfileAvatarForSuggestion(suggestion),
                caption, keyboardService.getReplyKeyboard(Keyboard.SEARCH_MENU)));
    }
}
