package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.FavouritesProfileDto;
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
public class MainMenuHandler implements UserInputHandler {
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

        if (text.equals(localeMessageService.getMessage("button.main.search"))) {
            UserProfileList userProfileList = new UserProfileList(restTemplateService.getSearchList(chatId));
            userDataCache.setUserProfileList(userId, userProfileList);

            ProfileDto currProf =  userProfileList.getCurrent();

            sendMessage.setText(currProf.getChatId() + currProf.getName());
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.main.profile"))) {
            sendMessage.setText(userDataCache.getUserProfileData(userId).toString());
        } else if (text.equals(localeMessageService.getMessage("button.main.favorite"))) {
            UserProfileList userProfileList = new UserProfileList(restTemplateService.getFavoriteList(chatId));

            if (userProfileList.isEmpty()) {
                botApiMethodList.add(new SendMessage(String.valueOf(chatId), "Вы еще никого не выбрали"));
                return botApiMethodList;
            }

            userDataCache.setUserProfileList(userId, userProfileList);

            ProfileDto currProf = userProfileList.getCurrent();

            sendMessage.setText(currProf.getChatId() + "=" + currProf.getName() + ": " + currProf.getStatus());
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.FAVORITE_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.FAVORITE_MENU);
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU));
        }

        botApiMethodList.add(sendMessage);
        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN_MENU;
    }
}
