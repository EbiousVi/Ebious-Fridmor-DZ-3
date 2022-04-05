package ru.liga.prereformdatingserver.domain.dto.auth;

import lombok.Data;

@Data
public class LoginDto {
    private final String chatId;
    private final String password;
}
