package ru.liga.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private String status;
    private Boolean isMatch;
    private byte[] avatar;
}