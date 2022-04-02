package ru.liga.prereformdatingserver.domain.dto.profileDto;

import lombok.*;
import org.springframework.core.io.Resource;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewProfileDto {
    private Long chatId;
    private String name;
    private String description;
    private Sex sex;
    private List<Sex> preferences;

}
