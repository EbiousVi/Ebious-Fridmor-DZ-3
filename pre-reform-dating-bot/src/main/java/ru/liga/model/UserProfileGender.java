package ru.liga.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum UserProfileGender {
    MALE("Сударь"),
    FEMALE("Сударыня");

    private final String value;

    public static UserProfileGender getByValue(String value) {
        return Stream.of(UserProfileGender.values())
                .filter(g -> g.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such gender value"));
    }
}
