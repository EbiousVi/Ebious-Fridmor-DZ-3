package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class FavoriteMenuHandler implements UserInputHandler {
    private final ReplyMessageService replyMessageService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserSuggestionList userSuggestionList = userDataCache.getUserProfileList(userId);

        if (text.equals(Button.LEFT.getValue())) {
            ProfileDto previousSuggestion = userSuggestionList.getPrevious();
            return replyMessageService.sendFavoritePhoto(chatId, previousSuggestion, Keyboard.FAVORITE_MENU);
        }

        if (text.equals(Button.RIGHT.getValue())) {
            ProfileDto nextSuggestion = userSuggestionList.getNext();
            return replyMessageService.sendFavoritePhoto(chatId, nextSuggestion, Keyboard.FAVORITE_MENU);
        }

        if (text.equals(Button.MAIN.getValue())) {
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
            return replyMessageService.sendPredeterminedMessage(
                    chatId, "reply.main.info", Keyboard.MAIN_MENU);
        }

        return replyMessageService.sendPredeterminedMessage(
                chatId, "reply.error.invalidValue", Keyboard.FAVORITE_MENU);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FAVORITE_MENU;
    }
}
