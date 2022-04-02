package ru.liga.prereformdatingserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Table("user_profile")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserProfile implements Persistable<Long> {

    @Id
    private Long id;

    private Long chatId;

    private String name;

    private String sex;

    private String description;

    private String avatar;
    @Transient
    @MappedCollection(idColumn = "chat_id")
    private List<Preferences> preferences;

    @Transient
    private Boolean isNew;

    public UserProfile(Long id, Long chatId, String name, String sex, String description,
                       String avatar, Boolean isNew) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.sex = sex;
        this.description = description;
        this.avatar = avatar;
        this.isNew = isNew;
    }

    public UserProfile(Long id, Long chatId, String name, String sex, String description,
                       String avatar, List<Preferences> preferences, Boolean isNew) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.sex = sex;
        this.description = description;
        this.avatar = avatar;
        this.preferences = preferences;
        this.isNew = isNew;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }
}
