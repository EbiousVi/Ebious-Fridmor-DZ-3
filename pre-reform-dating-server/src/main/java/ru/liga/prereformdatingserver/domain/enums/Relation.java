package ru.liga.prereformdatingserver.domain.enums;

public enum Relation {
    MY("Любим вами"),
    ME("Вы любимы"),
    MATCHES("Взаимность");

    public final String value;

    Relation(String value) {
        this.value = value;
    }
}
