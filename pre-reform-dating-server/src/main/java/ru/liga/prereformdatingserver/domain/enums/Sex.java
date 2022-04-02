package ru.liga.prereformdatingserver.domain.enums;

public enum Sex {
    MALE("Сударъ"),
    FEMALE("Сударыня");

    public final String name;

    Sex(String name) {
        this.name = name;
    }

    public static Sex getByValue(String sex) {
        for (Sex value : values()) {
            if (value.name.equals(sex)) {
                return value;
            }
        }
        throw new RuntimeException("enum expcetion sex");
    }
}
