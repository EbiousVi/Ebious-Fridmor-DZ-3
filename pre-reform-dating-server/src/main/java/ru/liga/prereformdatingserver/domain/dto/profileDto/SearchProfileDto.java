package ru.liga.prereformdatingserver.domain.dto.profileDto;

import lombok.*;
import org.springframework.core.io.Resource;
import ru.liga.prereformdatingserver.domain.enums.Favourites;
import ru.liga.prereformdatingserver.domain.enums.Sex;

@Data
public class SearchProfileDto {
    private Long chatId;
    private String name;
    private String sex;
    private Favourites favourites;
    private byte[] image;
}
