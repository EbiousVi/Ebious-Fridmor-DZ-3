package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
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
import ru.liga.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final ProfileImageService profileImageService;
    private final ReplyMessageService replyMessageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            ProfileDto previous = userProfileList.getPrevious();
            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForSuggestion(previous),
                    previous.getName() + ", " + previous.getSex() + ", " + previous.getStatus(), null));
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            ProfileDto next = userProfileList.getNext();
            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForSuggestion(next),
                    next.getName() + ", " + next.getSex() + ", " + next.getStatus(), null));
        } else if (text.equals(localeMessageService.getMessage("button.favorite.menu"))) {
            userDataCache.setUserCurrentBotState(userId, BotState.MAIN_MENU);

            return List.of(replyMessageService.getSendMessage(
                    chatId, localeMessageService.getMessage("reply.main.info"),
                    keyboardService.getReplyKeyboard(KeyboardName.MAIN_MENU)));
        } else {
            return List.of(replyMessageService.getSendMessage(
                    chatId, localeMessageService.getMessage("reply.error.invalidValue"), null));
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FAVORITE_MENU;
    }
}
