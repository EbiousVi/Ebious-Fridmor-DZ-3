package ru.liga.prereformtranslator.service.transaltor;

import ru.liga.prereformtranslator.service.domain.Description;

public interface TranslatorI {

    String translateText(String description);

    Description translateDescription(String text);
}
