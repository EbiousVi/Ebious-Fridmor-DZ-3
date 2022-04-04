package ru.liga;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.botapi.TelegramFacade;
import ru.liga.cache.UserDataCache;

@Slf4j
@Getter
@Setter
public class Bot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;

    private TelegramFacade telegramFacade;
    private UserDataCache userDataCache;

    public Bot(TelegramFacade telegramFacade, UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
        this.telegramFacade = telegramFacade;
    }

    @Override
    public void onUpdateReceived(Update update) {
        for (PartialBotApiMethod<?> partialBotApiMethod : telegramFacade.handleUpdate(update)) {
            executeMethod((partialBotApiMethod));
        }
    }

    private void executeMethod(PartialBotApiMethod<?> method) {
        try {
            if (method == null) {
            } else if (method instanceof SendPhoto) {
                execute((SendPhoto) method);
            } else if (method instanceof SendMessage) {
                execute((SendMessage) method);
            }
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
    }
}


