package ru.liga.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.model.UserProfileGender;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewProfileDto {
    private Long chatId;
    private String name;
    private String description;
    private UserProfileGender sex;
    private List<UserProfileGender> preferences;

}
