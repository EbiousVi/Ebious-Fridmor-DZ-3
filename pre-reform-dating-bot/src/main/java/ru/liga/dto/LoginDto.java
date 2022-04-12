package ru.liga.dto;

import lombok.Data;

@Data
public class LoginDto {
    private final String chatId;
    private final String password;
}
