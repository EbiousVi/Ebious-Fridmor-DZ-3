package ru.liga.prereformdatingserver.domain.dto.favouriteDto;

import lombok.Data;

@Data
public class FavouritesDto {
    private Long fromChatId;
    private Long toChatId;
}
