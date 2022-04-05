package ru.liga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileData {
    private long chatId;
    private String name;
    private UserProfileGender sex;
    private String description;
    private List<UserProfileGender> preferences;
    private byte[] avatar;
    @JsonIgnore
    @Builder.Default
    private UserProfileState profileState = UserProfileState.EMPTY_PROFILE;

}