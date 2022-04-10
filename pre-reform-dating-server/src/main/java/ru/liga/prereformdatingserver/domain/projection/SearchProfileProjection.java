package ru.liga.prereformdatingserver.domain.projection;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchProfileProjection {
    private final Long chatId;
    private final String name;
    private final String sex;
    private final String description;
    private final Boolean isPotentialMatch;
}