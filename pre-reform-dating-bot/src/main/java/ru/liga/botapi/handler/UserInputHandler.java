package ru.liga.botapi.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.BotState;

public interface UserInputHandler {
    SendMessage handle(Message message);
    BotState getHandlerName();
}
