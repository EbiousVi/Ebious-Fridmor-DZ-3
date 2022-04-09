package ru.liga.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {
    public ReplyKeyboard getReplyKeyboard(Keyboard keyboardName) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        switch (keyboardName) {
            case MAIN_MENU:
                replyKeyboardMarkup.setKeyboard(getMainMenuButtons());
                break;
            case SEARCH_MENU:
                replyKeyboardMarkup.setKeyboard(getSearchMenuButtons());
                break;
            case PROFILE_MENU:
                replyKeyboardMarkup.setKeyboard(getProfileMenuButtons());
                break;
            case UPDATE_MENU:
                replyKeyboardMarkup.setKeyboard(getUpdateMenuButtons());
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
            case REMOVE:
                return new ReplyKeyboardRemove(true, true);
            default:
                return null;
        }

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    private List<KeyboardRow> getMainMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.SEARCH.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.PROFILE.getValue());
        KeyboardRow row3 = new KeyboardRow();
        row3.add(Button.FAVORITE.getValue());

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        return keyboard;
    }

    private List<KeyboardRow> getSearchMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.LEFT.getValue());
        row1.add(Button.RIGHT.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.MAIN.getValue());

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getProfileMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.PROFILE.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.CHANGE_NAME.getValue());
        row2.add(Button.CHANGE_SEX.getValue());
        KeyboardRow row3 = new KeyboardRow();
        row3.add(Button.CHANGE_DESCRIPTION.getValue());
        row3.add(Button.CHANGE_PREFERENCE.getValue());
        KeyboardRow row4 = new KeyboardRow();
        row4.add(Button.MAIN.getValue());

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        return keyboard;
    }

    private List<KeyboardRow> getUpdateMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.APPLY.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.CANCEL.getValue());

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getLikeMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.CONTINUE.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.CHAT.getValue());

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getFavoriteMenuButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.LEFT.getValue());
        row1.add(Button.RIGHT.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.MAIN.getValue());

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

    private List<KeyboardRow> getGenderSelectButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(Button.MALE.getValue());
        row.add(Button.FEMALE.getValue());

        keyboard.add(row);

        return keyboard;
    }

    private List<KeyboardRow> getPreferenceSelectButtons() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Button.MALE.getValue());
        row1.add(Button.FEMALE.getValue());
        KeyboardRow row2 = new KeyboardRow();
        row2.add(Button.ALL.getValue());

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }
}
