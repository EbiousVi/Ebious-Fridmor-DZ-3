package ru.liga.prereformdatingserver.domain.dto.profileDto;

import lombok.*;
import ru.liga.prereformdatingserver.domain.enums.Favourites;

@Data
public class FavouritesProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private Favourites favourites;
    private byte[] avatar;
}
