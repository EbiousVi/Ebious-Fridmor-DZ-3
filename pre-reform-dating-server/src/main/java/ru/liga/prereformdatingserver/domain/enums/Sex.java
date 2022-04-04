package ru.liga.prereformdatingserver.domain.enums;

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
        throw new IllegalArgumentException("Unsupported sex type!");
    }
}
