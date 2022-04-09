package ru.liga.prereformtranslator.service.transaltor;

import ru.liga.prereformtranslator.service.domain.Description;

public interface Translator {

    String translateText(String text);

    Description translateDescription(String text);
}
