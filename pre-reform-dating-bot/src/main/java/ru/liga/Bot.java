package ru.liga;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.botapi.TelegramFacade;
import ru.liga.cache.UserDataCache;

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
        for (BotApiMethod<?> botApiMethod : telegramFacade.handleUpdate(update)) {
            try {
                execute(botApiMethod);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}


