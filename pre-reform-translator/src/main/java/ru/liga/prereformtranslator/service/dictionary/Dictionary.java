package ru.liga.prereformtranslator.service.dictionary;

public class Dictionary {

    private final String key;
    private final String value;

    public Dictionary(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
