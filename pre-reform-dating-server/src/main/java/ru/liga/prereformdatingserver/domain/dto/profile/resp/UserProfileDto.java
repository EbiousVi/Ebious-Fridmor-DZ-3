package ru.liga.prereformdatingserver.domain.dto.profile.resp;

import lombok.Builder;
import lombok.Data;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.List;

@Data
@Builder
public class UserProfileDto {
    private Long chatId;
    private String name;
    private Sex sex;
    private String description;
    private List<Sex> preferences;
    private byte[] avatar;
}
