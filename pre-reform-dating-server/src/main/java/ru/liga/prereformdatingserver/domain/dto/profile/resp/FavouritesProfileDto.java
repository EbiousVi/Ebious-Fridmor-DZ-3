package ru.liga.prereformdatingserver.domain.dto.profile.resp;

import lombok.*;

@Data
@Builder
public class FavouritesProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private String status;
    private byte[] avatar;
}
//любимцы, в статусе отношение к пользователю просто
//отобразить никаких проверок
