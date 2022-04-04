package ru.liga.botapi.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.BotState;

import java.util.List;

public interface UserInputHandler {
    List<PartialBotApiMethod<?>> handle(Message message);
    BotState getHandlerName();
}
