package ru.liga.prereformdatingserver.domain.dto.profile.resp;

import lombok.*;

@Data
@Builder
public class SearchProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private Boolean isMatch;
    private byte[] avatar;
}