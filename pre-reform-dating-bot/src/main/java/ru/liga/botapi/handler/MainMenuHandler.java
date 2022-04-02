package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.SearchProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainMenuHandler implements UserInputHandler {
    private final UserDataCache userDataCache;
    private final LocaleMessageService localeMessageService;
    private final KeyboardService keyboardService;
    private final RestTemplate restTemplate;

    @Override
    public SendMessage handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if (text.equals(localeMessageService.getMessage("button.main.search"))) {
            //запрос на сервер за списком совпадающих пользователей
            List<SearchProfileDto> rest = restSearch(chatId);
            UserProfileList userProfileList = new UserProfileList(rest);
            userDataCache.setUserProfileList(userId, userProfileList);
            //выводим на экран первую анкету из списка
            sendMessage.setText(userProfileList.getCurrent().getChatId() + userProfileList.getCurrent().getName());
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.SEARCH_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.main.profile"))) {
            //выводим на экран анкету пользователя
            sendMessage.setText(userDataCache.getUserProfileData(userId).toString());
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.MAIN_MENU));
        } else if (text.equals(localeMessageService.getMessage("button.main.favorite"))) {
            //запрос на сервер за списком лайкнутых пользователей
            List<SearchProfileDto> searchProfileDtos = restFavourite(chatId);
            UserProfileList userProfileList = new UserProfileList(searchProfileDtos);
            userDataCache.setUserProfileList(userId, userProfileList);
            sendMessage.setText(userProfileList.getCurrent().toString());
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.FAVORITE_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.FAVORITE_MENU);
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
        }
        return sendMessage;
    }

    public List<SearchProfileDto> restFavourite(long chatId) {
        try {
            ResponseEntity<SearchProfileDto[]> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/favourites/" + chatId, SearchProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return List.of(resp.getBody());
            } else {
                throw new RuntimeException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<SearchProfileDto> restSearch(long chatId) {
        try {
            ResponseEntity<SearchProfileDto[]> resp = restTemplate.getForEntity("http://localhost:6064/dating-server/search/for/" + chatId, SearchProfileDto[].class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return List.of(resp.getBody());
            } else {
                throw new RuntimeException("Pre reform translator return bad response!");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN_MENU;
    }
}
