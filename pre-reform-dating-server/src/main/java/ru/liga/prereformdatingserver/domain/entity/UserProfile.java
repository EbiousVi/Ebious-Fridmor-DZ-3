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

    @Column("avatar")
    private String avatar;

    @Transient
    @EqualsAndHashCode.Exclude
    private Boolean isNew;

    @MappedCollection(idColumn = "chat_id")
    private Set<Preferences> preferences;

    @Builder
    public UserProfile(Long chatId, String password, String name, String sex,
                       String description, String avatar, Boolean isNew,
                       Set<Preferences> preferences) {
        this.chatId = chatId;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.description = description;
        this.avatar = avatar;
        this.isNew = isNew;
        this.preferences = preferences;
    }

    @PersistenceConstructor
    public UserProfile(Long chatId,
                       String name, String sex,
                       String description, String avatar,
                       Set<Preferences> preferences) {
        this.chatId = chatId;
        this.name = name;
        this.sex = sex;
        this.description = description;
        this.avatar = avatar;
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

    public void addPreferences(Set<Preferences> preferences) {
        this.preferences.addAll(preferences);
    }
}
