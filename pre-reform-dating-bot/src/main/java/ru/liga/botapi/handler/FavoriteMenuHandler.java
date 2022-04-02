package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavoriteMenuHandler implements UserInputHandler {
    private final UserDataCache userDataCache;
    private final LocaleMessageService localeMessageService;
    private final KeyboardService keyboardService;

    @Override
    public SendMessage handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.favorite.menu"))) {
            sendMessage.setText("Главное меню");
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.MAIN_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            //здесь должна быть проверка по взаимным лайкам придется опять обращаться к серверу
            sendMessage.setText(userProfileList.getPrevious().toString());
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            //здесь должна быть проверка по взаимным лайкам придется опять обращаться к серверу
            sendMessage.setText(userProfileList.getNext().toString());
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
        }

        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FAVORITE_MENU;
    }
}
