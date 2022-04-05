package ru.liga.Dto;

import lombok.Builder;
import lombok.Data;
import ru.liga.model.UserProfileGender;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserProfileDto {
    private Long chatId;
    private String name;
    private UserProfileGender sex;
    private String description;
    private List<UserProfileGender> preferences;
    private Map<String, String> tokens;
    private byte[] avatar;
}
