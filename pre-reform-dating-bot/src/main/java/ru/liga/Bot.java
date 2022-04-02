package ru.liga;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.botapi.TelegramFacade;

@Getter
@Setter
public class Bot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;

    private TelegramFacade telegramFacade;

    public Bot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(telegramFacade.handleUpdate(update));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


