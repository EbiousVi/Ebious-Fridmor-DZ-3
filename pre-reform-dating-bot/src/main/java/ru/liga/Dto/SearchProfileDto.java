package ru.liga.Dto;

import lombok.Data;


@Data
public class SearchProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private Favourites favourites;
    private byte[] image;
}
