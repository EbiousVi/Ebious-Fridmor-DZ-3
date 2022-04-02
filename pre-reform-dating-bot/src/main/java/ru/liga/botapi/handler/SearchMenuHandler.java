package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.FavouritesDto;
import ru.liga.Dto.SearchProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchMenuHandler implements UserInputHandler {

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

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.search.menu"))) {
            sendMessage.setText("Главное меню");
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(Keyboard.MAIN_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            sendMessage.setText(userProfileList.getNext().toString());
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            //ставим лайк обращаемся к серверу и проверяем есть ли взаимный лайк
            SearchProfileDto next = userProfileList.getNext();
            sendMessage.setText(next.getChatId() + "=" + next.getName());
            restChooseAFavourite(chatId, next.getChatId());
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
        }

        return sendMessage;
    }


    public void restChooseAFavourite(Long fromChatId, Long toChatId) {
        try {
            HttpEntity<FavouritesDto> request = new HttpEntity<>(new FavouritesDto(fromChatId, toChatId));

            ResponseEntity<Void> resp = restTemplate.postForEntity("http://localhost:6064/dating-server/favourites/like", request, Void.class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return;
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
        return BotState.SEARCH_MENU;
    }
}
