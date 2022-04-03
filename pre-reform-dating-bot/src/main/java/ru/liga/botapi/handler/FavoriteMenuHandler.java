package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.SearchProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.KeyboardName;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavoriteMenuHandler implements UserInputHandler {
    private final UserDataCache userDataCache;
    private final LocaleMessageService localeMessageService;
    private final KeyboardService keyboardService;

    @Override
    public List<BotApiMethod<?>> handle(Message message) {
        List<BotApiMethod<?>> botApiMethodList = new ArrayList<>();

        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.favorite.menu"))) {
            sendMessage.setText(localeMessageService.getMessage("reply.main.info"));
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            SearchProfileDto searchProfileDto = userProfileList.getPrevious();
            String likesInfo = searchProfileDto.getFavourites().value;
            if (likesInfo != null) {
                sendMessage.setText(searchProfileDto.getChatId() + "=" + searchProfileDto.getName() + ": " + likesInfo);
            } else {
                sendMessage.setText(searchProfileDto.getChatId() + "=" + searchProfileDto.getName() + ": no info about likes");
            }
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            SearchProfileDto searchProfileDto = userProfileList.getNext();
            String likesInfo = searchProfileDto.getFavourites().value;
            if (likesInfo != null) {
                sendMessage.setText(searchProfileDto.getChatId() + "=" + searchProfileDto.getName() + ": " + likesInfo);
            } else {
                sendMessage.setText(searchProfileDto.getChatId() + "=" + searchProfileDto.getName() + ": no info about likes");
            }
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
        }

        botApiMethodList.add(sendMessage);

        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FAVORITE_MENU;
    }
}
