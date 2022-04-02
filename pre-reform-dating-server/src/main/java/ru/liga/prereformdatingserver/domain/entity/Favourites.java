package ru.liga.prereformdatingserver.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table("favourites")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Favourites {

    @Id
    private Long id;

    private Long fromChatId;

    private Long toChatId;
}
