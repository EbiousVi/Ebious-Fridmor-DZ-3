package ru.liga.prereformtranslator.service.transaltor;

import ru.liga.prereformtranslator.service.domain.Domain;

public interface TranslatorI {

    Domain translateToObject(String text);

    String translateToString(String text);
}
