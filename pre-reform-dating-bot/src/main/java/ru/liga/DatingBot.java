package ru.liga;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DatingBot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;

    private final TelegramFacade telegramFacade;
    private final UserDataCache userDataCache;

    @Override
    public void onUpdateReceived(Update update) {
        for (PartialBotApiMethod<?> partialBotApiMethod : telegramFacade.handleUpdate(update)) {
            executeMethod((partialBotApiMethod));
        }
    }

    private void executeMethod(PartialBotApiMethod<?> method) {
        try {
            if (method instanceof SendPhoto) {
                execute((SendPhoto) method);
            }
            if (method instanceof SendMessage) {
                execute((SendMessage) method);
            }
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
    }
}


