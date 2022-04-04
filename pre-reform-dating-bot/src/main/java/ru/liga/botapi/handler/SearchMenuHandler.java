package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.Favourites;
import ru.liga.Dto.FavouritesDto;
import ru.liga.Dto.ProfileDto;
import ru.liga.Dto.SearchProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.KeyboardName;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.RestTemplateService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchMenuHandler implements UserInputHandler {

    private final LocaleMessageService localeMessageService;
    private final RestTemplateService restTemplateService;
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

        if (text.equals(localeMessageService.getMessage("button.search.menu"))) {
            sendMessage.setText(localeMessageService.getMessage("reply.main.info"));
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            ProfileDto next =  userProfileList.getNext();
            sendMessage.setText(next.getChatId() + "=" + next.getName());
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            ProfileDto current =  userProfileList.getCurrent();
            restTemplateService.setFavoriteUser(chatId, current.getChatId());
            if (current.getIsMatch()) {
                sendMessage.setText("Вы любимы");
                sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.LIKE_MENU));
                userDataCache.setUserCurrentBotState(userId, BotState.LIKE_MENU);
            } else {
                ProfileDto next =  userProfileList.getNext();
                sendMessage.setText(next.getChatId() + "=" + next.getName());
            }
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
        }

        botApiMethodList.add(sendMessage);

        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SEARCH_MENU;
    }
}
