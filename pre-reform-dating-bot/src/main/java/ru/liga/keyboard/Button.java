package ru.liga.keyboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Button {
    MAIN("Меню"),
    SEARCH("Поиск"),
    PROFILE("Анкета"),
    FAVORITE("Любимцы"),
    LEFT("Влево"),
    RIGHT("Вправо"),
    MALE("Сударь"),
    FEMALE("Сударыня"),
    ALL("Всех"),
    CONTINUE("Продолжить"),
    CHAT("Написать"),
    CHANGE_NAME("Изменить имя"),
    CHANGE_SEX("Изменить пол"),
    CHANGE_DESCRIPTION("Изменить описание"),
    CHANGE_PREFERENCE("Изменить предпочтения"),
    APPLY("Принять"),
    CANCEL("Отмена");



    private final String value;
}
