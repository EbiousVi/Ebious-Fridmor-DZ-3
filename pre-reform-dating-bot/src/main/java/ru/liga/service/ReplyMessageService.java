package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.liga.cache.UserDataCache;
import ru.liga.dto.ProfileDto;
import ru.liga.keyboard.Keyboard;
import ru.liga.keyboard.KeyboardService;
import ru.liga.model.UserProfileData;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyMessageService {
    private final LocaleMessageService localeMessageService;
    private final ProfileImageService profileImageService;
    private final KeyboardService keyboardService;

    public List<PartialBotApiMethod<?>> sendPredeterminedMessage(long chatId, String message, Keyboard keyboardName) {
        return List.of(getSendMessage(
                chatId, localeMessageService.getMessage(message), keyboardService.getReplyKeyboard(keyboardName)));
    }

    public List<PartialBotApiMethod<?>> sendCustomMessage(long chatId, String message, Keyboard keyboardName) {
        return List.of(getSendMessage(
                chatId, message, keyboardService.getReplyKeyboard(keyboardName)));
    }

    public List<PartialBotApiMethod<?>> sendUserPhoto(long chatId, UserProfileData userProfileData, Keyboard keyboardName) {
        String caption = userProfileData.getName() + ", " + userProfileData.getSex().getValue();
        return List.of(getSendPhoto(
                chatId, profileImageService.getProfileAvatarForUser(chatId),
                caption, keyboardService.getReplyKeyboard(keyboardName)));
    }

    public List<PartialBotApiMethod<?>> sendSearchPhoto(long chatId, ProfileDto favorite, Keyboard keyboardName) {
        String caption = favorite.getName() + ", " + favorite.getSex();
        return List.of(getSendPhoto(
                chatId, profileImageService.getProfileAvatarForSuggestion(favorite),
                caption, keyboardService.getReplyKeyboard(keyboardName)));
    }

    public List<PartialBotApiMethod<?>> sendFavoritePhoto(long chatId, ProfileDto favorite, Keyboard keyboardName) {
        String caption = favorite.getName() + ", " + favorite.getSex() + ", " + favorite.getStatus();
        return List.of(getSendPhoto(
                chatId, profileImageService.getProfileAvatarForSuggestion(favorite),
                caption, keyboardService.getReplyKeyboard(keyboardName)));
    }

    private SendMessage getSendMessage(long chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        sendMessage.setReplyMarkup(replyKeyboard);
        return sendMessage;
    }

    private SendPhoto getSendPhoto(long chatId, InputFile file, String caption, ReplyKeyboard replyKeyboard) {
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), file);
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(replyKeyboard);
        return sendPhoto;
    }
}
