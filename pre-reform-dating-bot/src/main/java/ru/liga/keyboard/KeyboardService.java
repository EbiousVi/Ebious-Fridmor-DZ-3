package ru.liga.keyboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.liga.service.LocaleMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardService {
    private final LocaleMessageService messageService;

    public ReplyKeyboardMarkup getReplyKeyboard(KeyboardName keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        switch (keyboard) {
            case MAIN_MENU:
                replyKeyboardMarkup.setKeyboard(getMainMenuButtons());
                break;
            case SEARCH_MENU:
                replyKeyboardMarkup.setKeyboard(getSearchMenuButtons());
                break;
            case LIKE_MENU:
                replyKeyboardMarkup.setKeyboard(getLikeMenuButtons());
                break;
            case FAVORITE_MENU:
                replyKeyboardMarkup.setKeyboard(getFavoriteMenuButtons());
                break;
            case GENDER_SELECT:
                replyKeyboardMarkup.setKeyboard(getGenderSelectButtons());
                break;
            case PREFERENCE_SELECT:
                replyKeyboardMarkup.setKeyboard(getPreferenceSelectButtons());
                break;
        }

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    private List<KeyboardRow> getMainMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(messageService.getMessage("button.main.search"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(messageService.getMessage("button.main.profile"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(messageService.getMessage("button.main.favorite"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        return keyboard;
    }

    private List<KeyboardRow> getSearchMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(messageService.getMessage("button.search.left"));
        row1.add(messageService.getMessage("button.search.right"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(messageService.getMessage("button.search.menu"));

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getLikeMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(messageService.getMessage("button.like.next"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(messageService.getMessage("button.like.chat"));

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getFavoriteMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(messageService.getMessage("button.favorite.left"));
        row1.add(messageService.getMessage("button.favorite.right"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(messageService.getMessage("button.favorite.menu"));

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getGenderSelectButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(messageService.getMessage("button.gender.male"));
        row.add(messageService.getMessage("button.gender.female"));

        keyboard.add(row);

        return keyboard;
    }

    private List<KeyboardRow> getPreferenceSelectButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(messageService.getMessage("button.preference.male"));
        row1.add(messageService.getMessage("button.preference.female"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(messageService.getMessage("button.preference.all"));

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }
}
