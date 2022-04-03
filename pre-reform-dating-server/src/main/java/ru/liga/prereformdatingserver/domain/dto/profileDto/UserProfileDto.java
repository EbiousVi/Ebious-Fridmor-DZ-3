package ru.liga.prereformdatingserver.domain.dto.profileDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.List;

@Data
@Builder
public class UserProfileDto {
    private Long chatId;
    private String name;
    private String description;
    private Sex sex;
    private List<Sex> preferences;
}
