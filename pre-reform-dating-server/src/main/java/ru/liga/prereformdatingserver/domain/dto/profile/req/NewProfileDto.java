package ru.liga.prereformdatingserver.domain.dto.profile.req;

import lombok.*;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.nio.file.Path;
import java.util.List;

@Data
@Builder
public class NewProfileDto {
    private Long chatId;
    private String name;
    private String description;
    private Path avatar;
    private Sex sex;
    private List<Sex> preferences;
}
