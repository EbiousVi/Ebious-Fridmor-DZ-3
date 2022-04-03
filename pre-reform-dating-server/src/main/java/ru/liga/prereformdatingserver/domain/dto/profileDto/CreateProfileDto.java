package ru.liga.prereformdatingserver.domain.dto.profileDto;

import lombok.*;

@Data
public class CreateProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private byte[] image;
}
