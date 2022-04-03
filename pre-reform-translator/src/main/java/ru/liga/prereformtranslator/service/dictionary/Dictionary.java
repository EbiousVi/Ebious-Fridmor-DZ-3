package ru.liga.prereformtranslator.service.dictionary;

import lombok.Data;

@Data
public class Dictionary {

    private final String key;
    private final String value;

    public Dictionary(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
