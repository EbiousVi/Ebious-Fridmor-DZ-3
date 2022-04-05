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
import ru.liga.model.UserProfileData;
import ru.liga.model.UserProfileList;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ProfileImageService;
import ru.liga.service.RestTemplateService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final RestTemplateService restTemplateService;
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

        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);

        if (text.equals(localeMessageService.getMessage("button.main.search"))) {
            UserProfileList userProfileList = new UserProfileList(restTemplateService.getSearchList(chatId));
            if (userProfileList.isEmpty()) {
                sendMessage.setText("В данный момент нет анкет, совпадающих по поисковому критерию.");
                botApiMethodList.add(sendMessage);
                return botApiMethodList;
            }
            userDataCache.setUserProfileList(userId, userProfileList);

            ProfileDto currentSuggestion = userProfileList.getCurrent();
            setSendPhotoOptions(sendPhoto, currentSuggestion);
            sendPhoto.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU));
            botApiMethodList.add(sendPhoto);

            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);
        } else if (text.equals(localeMessageService.getMessage("button.main.profile"))) {
            sendPhoto.setPhoto(new InputFile(profileImageService.getProfileImageForUser(userId)));
            sendPhoto.setCaption(userProfileData.getName() + ", " + userProfileData.getSex().getValue());
            botApiMethodList.add(sendPhoto);
        } else if (text.equals(localeMessageService.getMessage("button.main.favorite"))) {
            UserProfileList userProfileList = new UserProfileList(restTemplateService.getFavoriteList(chatId));
            if (userProfileList.isEmpty()) {
                sendMessage.setText("Вы еще никого не выбрали.");
                botApiMethodList.add(sendMessage);
                return botApiMethodList;
            }
            userDataCache.setUserProfileList(userId, userProfileList);

            ProfileDto currentSuggestion = userProfileList.getCurrent();
            setSendPhotoOptions(sendPhoto, currentSuggestion);
            sendPhoto.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.FAVORITE_MENU));
            botApiMethodList.add(sendPhoto);

            userDataCache.setUserCurrentBotState(userId, BotState.FAVORITE_MENU);
        } else {
            sendMessage.setText(localeMessageService.getMessage("reply.error.invalidValue"));
            sendMessage.setReplyMarkup(keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU));
            botApiMethodList.add(sendMessage);
            return botApiMethodList;
        }

        return botApiMethodList;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN_MENU;
    }

    private void setSendPhotoOptions(SendPhoto sendPhoto, ProfileDto Suggestion) {
        sendPhoto.setPhoto(new InputFile(profileImageService.getProfileImageForSuggestion(Suggestion)));
        sendPhoto.setCaption(Suggestion.getName() + ", " + Suggestion.getSex());
    }
}
