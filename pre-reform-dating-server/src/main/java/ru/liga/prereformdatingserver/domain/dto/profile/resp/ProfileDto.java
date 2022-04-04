package ru.liga.prereformdatingserver.domain.dto.profile.resp;

import lombok.*;

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