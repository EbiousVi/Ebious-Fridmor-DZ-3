package ru.liga.Dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Favourites {
    MY("Любим вами"),
    ME("Вы любимы"),
    MATCHES("Взаимность");

    public final String value;
}
