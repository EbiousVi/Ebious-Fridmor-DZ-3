package ru.liga.prereformdatingserver.domain.dto.favourites.req;

import lombok.Data;

@Data
public class FavouritesDto {
    private final Long fromChatId;
    private final Long toChatId;
}
