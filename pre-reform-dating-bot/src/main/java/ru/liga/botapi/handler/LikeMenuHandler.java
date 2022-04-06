package ru.liga.botapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.Dto.ProfileDto;
import ru.liga.botapi.BotState;
import ru.liga.cache.UserDataCache;
import ru.liga.keyboard.KeyboardName;
import ru.liga.keyboard.KeyboardService;
import ru.liga.service.LocaleMessageService;
import ru.liga.service.ProfileImageService;
import ru.liga.service.ReplyMessageService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeMenuHandler implements UserInputHandler {
    private final LocaleMessageService localeMessageService;
    private final ReplyMessageService replyMessageService;
    private final ProfileImageService profileImageService;
    private final KeyboardService keyboardService;
    private final UserDataCache userDataCache;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(localeMessageService.getMessage("button.like.next"))) {
            userDataCache.setUserCurrentBotState(userId, BotState.SEARCH_MENU);

            if (userDataCache.getUserProfileList(userId).isEmpty()) {
                return List.of(replyMessageService.getSendMessage(
                        chatId, localeMessageService.getMessage("reply.list.suggestionIsOver"),
                        keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU)));
            } else {
                ProfileDto nextSuggestion = userDataCache.getUserProfileList(userId).getNext();

                return List.of(replyMessageService.getSendPhoto(
                        chatId, profileImageService.getProfileImageForSuggestion(nextSuggestion),
                        nextSuggestion.getName() + ", " + nextSuggestion.getSex(),
                        keyboardService.getReplyKeyboard(KeyboardName.SEARCH_MENU)));
            }
        } else if (text.equals(localeMessageService.getMessage("button.like.chat"))) {
            return List.of(replyMessageService.getSendMessage(
                    chatId, localeMessageService.getMessage("reply.like.inDevelopment"), null));
        } else {
            return List.of(replyMessageService.getSendMessage(
                    chatId, localeMessageService.getMessage("reply.error.invalidValue"), null));
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.LIKE_MENU;
    }
}
