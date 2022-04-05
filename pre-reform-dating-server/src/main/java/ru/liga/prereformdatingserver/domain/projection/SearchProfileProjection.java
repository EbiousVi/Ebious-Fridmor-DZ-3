package ru.liga.prereformdatingserver.domain.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchProfileProjection {
    private Long chatId;
    private String name;
    private String sex;
    private String avatar;
    private Boolean isPotentialMatch;
}