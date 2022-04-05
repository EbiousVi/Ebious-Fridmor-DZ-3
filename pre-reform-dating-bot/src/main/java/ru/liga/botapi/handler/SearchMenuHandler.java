package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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
import ru.liga.service.RestTemplateService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final RestTemplateService restTemplateService;
    private final ReplyMessageService replyMessageService;
    private final ProfileImageService profileImageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        UserProfileList userProfileList = userDataCache.getUserProfileList(userId);

        if (text.equals(localeMessageService.getMessage("button.search.left"))) {
            if (userProfileList.isEmpty()) {
                return List.of(listIsOver(chatId));
            }

            ProfileDto nextSuggestion = userProfileList.getNext();
            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForSuggestion(nextSuggestion),
                    nextSuggestion.getName() + ", " + nextSuggestion.getSex(), null));
        } else if (text.equals(localeMessageService.getMessage("button.search.right"))) {
            if (userProfileList.isEmpty()) {
                return List.of(listIsOver(chatId));
            }

            ProfileDto currentSuggestion = userProfileList.getCurrent();
            userProfileList.removeCurrent();

            restTemplateService.setFavoriteUser(userDataCache.getUserProfileData(chatId), currentSuggestion.getChatId());

            if (currentSuggestion.getIsMatch()) {
                userDataCache.setUserCurrentBotState(userId, BotState.LIKE_MENU);
                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.search.reciprocity"),
                        keyboardService.getReplyKeyboard(KeyboardName.LIKE_MENU)));
            }

            ProfileDto nextSuggestion = userProfileList.getNext();
            return List.of(replyMessageService.getSendPhoto(
                    chatId, profileImageService.getProfileImageForSuggestion(nextSuggestion),
                    nextSuggestion.getName() + ", " + nextSuggestion.getSex(), null));
        } else if (text.equals(localeMessageService.getMessage("button.search.menu"))) {
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
        return BotState.SEARCH_MENU;
    }

    private SendMessage listIsOver(long chatId) {
        return replyMessageService.getSendMessage(
                chatId, localeMessageService.getMessage("reply.list.suggestionIsOver"), null);
    }
}
