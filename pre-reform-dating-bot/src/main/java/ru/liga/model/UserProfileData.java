package ru.liga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileData {
    private long chatId;
    private String name;
    private UserProfileGender sex;
    private String description;
    private List<UserProfileGender> preferences;
    private byte[] avatar;
    @JsonIgnore
    private UserProfileState profileState = UserProfileState.EMPTY_PROFILE;
}