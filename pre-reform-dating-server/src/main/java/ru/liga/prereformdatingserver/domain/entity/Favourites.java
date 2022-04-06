package ru.liga.prereformdatingserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;


@Table("favourites")
@Data
public class Favourites {

    @Id
    private final Long id;

    private final Long fromChatId;

    private final Long toChatId;

    public Favourites(Long fromChatId, Long toChatId) {
        this.id = null;
        this.fromChatId = fromChatId;
        this.toChatId = toChatId;
    }

    @PersistenceConstructor
    public Favourites(Long id, Long fromChatId, Long toChatId) {
        this.id = id;
        this.fromChatId = fromChatId;
        this.toChatId = toChatId;
    }
}
