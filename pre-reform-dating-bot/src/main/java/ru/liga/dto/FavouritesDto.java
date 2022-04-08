package ru.liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavouritesDto {
    private Long fromChatId;
    private Long toChatId;
}
