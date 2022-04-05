package ru.liga.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Service
public class ReplyMessageService {

    public SendMessage getSendMessage(long chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        sendMessage.setReplyMarkup(replyKeyboard);
        return sendMessage;
    }

    public SendPhoto getSendPhoto(long chatId, InputFile file, String caption, ReplyKeyboard replyKeyboard) {
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), file);
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(replyKeyboard);
        return sendPhoto;
    }
}
