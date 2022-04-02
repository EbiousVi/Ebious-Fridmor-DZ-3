package ru.liga.prereformtranslator.controller;

public enum Sex {
    MALE("Сударъ"),
    FEMALE("Сударыня");

    public final String name;

    Sex(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
