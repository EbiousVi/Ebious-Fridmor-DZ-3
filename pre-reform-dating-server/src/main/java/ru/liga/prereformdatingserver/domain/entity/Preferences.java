package ru.liga.prereformdatingserver.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("preferences")
@Data
public class Preferences {

    @Id
    @Column("id")
    private final Long id;

    @Column("chat_id")
    private final Long chatId;

    @Column("sex")
    private final String sex;

    @PersistenceConstructor
    public Preferences(Long id, Long chatId, String sex) {
        this.id = null;
        this.chatId = chatId;
        this.sex = sex;
    }

    public Preferences(Long chatId, String sex) {
        this.id = null;
        this.chatId = chatId;
        this.sex = sex;
    }
}
