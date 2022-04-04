package ru.liga.botapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.cache.UserDataCache;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;


    public List<PartialBotApiMethod<?>> handleUpdate(Update update) {
        List<PartialBotApiMethod<?>> replyMessage = null;

        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private List<PartialBotApiMethod<?>> handleInputMessage(Message message) {
        long userId = message.getFrom().getId();
        BotState botState = userDataCache.getUserCurrentBotState(userId);
        return botStateContext.processInputMessage(botState, message);
    }
}
