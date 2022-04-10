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
import ru.liga.model.UserSuggestionList;
import ru.liga.service.ReplyMessageService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeMenuHandler implements UserInputHandler {
    private final ReplyMessageService replyMessageService;
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
                return replyMessageService.sendPredeterminedMessage(
                        chatId, "reply.list.suggestionIsOver", Keyboard.SEARCH_MENU);
            }
            ProfileDto nextSuggestion = userSuggestionList.getNext();
            return replyMessageService.sendSearchPhoto(chatId, nextSuggestion, Keyboard.SEARCH_MENU);
        }

        if (text.equals(Button.CHAT.getValue())) {
            return replyMessageService.sendPredeterminedMessage(
                    chatId, "reply.like.inDevelopment", Keyboard.LIKE_MENU);
        }

        return replyMessageService.sendPredeterminedMessage(
                chatId, "reply.error.invalidValue", Keyboard.LIKE_MENU);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.LIKE_MENU;
    }
}
