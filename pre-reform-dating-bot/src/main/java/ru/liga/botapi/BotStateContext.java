package ru.liga.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.handler.UserInputHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, UserInputHandler> messageHandlerMap = new HashMap<>();

    public BotStateContext(List<UserInputHandler> messageHandlerList) {
        messageHandlerList.forEach(handler -> this.messageHandlerMap.put(handler.getHandlerName(), handler));
    }

    public List<PartialBotApiMethod<?>> processInputMessage(BotState currentState, Message message) {
        UserInputHandler currentMessageHandler = messageHandlerMap.get(currentState);
        return currentMessageHandler.handle(message);
    }
}
