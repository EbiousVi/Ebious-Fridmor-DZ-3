package ru.liga.prereformdatingserver.domain.enums;

public enum Favourites {
    MY("Любим вами"),
    ME("Вы любимы"),
    MATCHES("Взаимность");

    public final String value;

    Favourites(String value) {
        this.value = value;
    }
}
