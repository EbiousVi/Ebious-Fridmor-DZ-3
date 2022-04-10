package ru.liga.prereformdatingserver.domain.dto.profile.req;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.liga.prereformdatingserver.domain.enums.Sex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.List;

@Data
@Builder
@Validated
public class NewProfileDto {

    @NotNull(message = "dto -> chatId is null")
    private Long chatId;

    @NotNull(message = "dto -> name is null")
    private String name;

    @NotNull(message = "dto -> description is null")
    private String description;

    @NotNull(message = "dto -> sex is null")
    private Sex sex;

    @NotEmpty(message = "dto -> preferences is empty or null")
    private List<Sex> preferences;
}
