package ru.liga.prereformdatingserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;

import java.util.Set;

@Table("user_profile")
@Data
@Builder
@AllArgsConstructor
public class UserProfile implements Persistable<Long> {

    @Id
    @Column("chat_id")
    private Long chatId;

    @Column("password")
    private String password;

    @Column("name")
    private String name;

    @Column("sex")
    private String sex;

    @Column("description")
    private String description;

    @Transient
    @EqualsAndHashCode.Exclude
    private Boolean isNew;

    @MappedCollection(idColumn = "chat_id")
    private Set<Preferences> preferences;

    @PersistenceConstructor
    public UserProfile(Long chatId, String name, String sex,
                       String description, Set<Preferences> preferences) {
        this.chatId = chatId;
        this.name = name;
        this.sex = sex;
        this.description = description;
        this.preferences = preferences;
        this.isNew = false;
    }

    @Override
    public Long getId() {
        return chatId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
