package ru.liga.prereformdatingserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Table("user_profile")
@Data
public class UserProfile implements Persistable<Long>, UserDetails {

    @Id
    @Column("chat_id")
    private Long chatId;
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
    public UserProfile(Long chatId, String name, String sex,
                       String description, String avatar, Boolean isNew,
                       Set<Preferences> preferences) {
        this.chatId = chatId;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return String.valueOf(chatId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
