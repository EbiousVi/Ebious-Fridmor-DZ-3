package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.ProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.KeyboardName;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ProfileImageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final ProfileImageService profileImageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        List<PartialBotApiMethod<?>> botApiMethodList = new ArrayList<>();

        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.favorite.menu"))) {
            sendMessage.setText(localeMessageService.getMessage("reply.main.info"));
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);
            botApiMethodList.add(sendMessage);
        } else if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            ProfileDto nextSuggestion = userProfileList.getPrevious();
            setSendPhotoOptions(sendPhoto, nextSuggestion);
            botApiMethodList.add(sendPhoto);
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            ProfileDto nextSuggestion = userProfileList.getNext();
            setSendPhotoOptions(sendPhoto, nextSuggestion);
            botApiMethodList.add(sendPhoto);
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
            botApiMethodList.add(sendMessage);
        }

        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FAVORITE_MENU;
    }

    private void setSendPhotoOptions(SendPhoto sendPhoto, ProfileDto suggestion) {
        sendPhoto.setPhoto(new InputFile(profileImageService.getProfileImageForSuggestion(suggestion)));
        sendPhoto.setCaption(suggestion.getName() + ", " + suggestion.getSex() + ", " + suggestion.getStatus());
    }
}
