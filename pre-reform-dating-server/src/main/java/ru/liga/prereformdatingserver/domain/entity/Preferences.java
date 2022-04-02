package ru.liga.prereformdatingserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("preferences")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Preferences {

    @Id
    private Long id;

    private Long chatId;

    private String sex;

}
