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

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeMenuHandler implements UserInputHandler {
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

        if (text.equals(localeMessageService.getMessage("button.like.next"))) {
            ProfileDto nextSuggestion = userProfileList.getNext();
            setSendPhotoOptions(sendPhoto, nextSuggestion);
            sendPhoto.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU));
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
            botApiMethodList.add(sendPhoto);
        } else if (text.equals(localeMessageService.getMessage("button.like.chat"))) {
            sendMessage.setText("Функционал не доступен, перейдите к следующей анкете");
            botApiMethodList.add(sendMessage);
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
            botApiMethodList.add(sendMessage);
        }

        botApiMethodList.add(sendMessage);

        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.LIKE_MENU;
    }

    private void setSendPhotoOptions(SendPhoto sendPhoto, ProfileDto Suggestion) {
        sendPhoto.setPhoto(new InputFile(profileImageService.getProfileImageForSuggestion(Suggestion)));
        sendPhoto.setCaption(Suggestion.getName() + ", " + Suggestion.getSex());
    }
}
