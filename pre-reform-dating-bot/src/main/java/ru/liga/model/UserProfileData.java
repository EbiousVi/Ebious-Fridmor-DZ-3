package ru.liga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileData {
    private long chatId;
    private String name;
    private UserProfileGender sex;
    private String description;
    private Map<String, String> tokens;
    private List<UserProfileGender> preferences;
    private byte[] avatar;
    @JsonIgnore
    private UserProfileState profileState = UserProfileState.EMPTY_PROFILE;
}