package ru.liga.prereformdatingserver.domain.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Sex {
    MALE("Сударъ"),
    FEMALE("Сударыня");

    public final String name;

    Sex(String name) {
        this.name = name;
    }

    public static Sex getByValue(String value) {
        for (Sex sex : values()) {
            if (sex.name.equals(value)) return sex;
        }
        log.error("Unexpected case, illegal Sex type!");
        throw new IllegalArgumentException("Unsupported sex type");
    }
}
