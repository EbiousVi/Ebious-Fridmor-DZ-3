package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.ProfileDto;
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
public class LikeMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<BotApiMethod<?>> handle(Message message) {
        List<BotApiMethod<?>> botApiMethodList = new ArrayList<>();

        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.like.next"))) {
            ProfileDto next = userProfileList.getNext();
            sendMessage.setText(next.getChatId() + "=" + next.getName());
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.like.chat"))) {
            sendMessage.setText("Функционал не доступен, перейдите к следующей анкете");
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
        }

        botApiMethodList.add(sendMessage);

        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.LIKE_MENU;
    }
}
