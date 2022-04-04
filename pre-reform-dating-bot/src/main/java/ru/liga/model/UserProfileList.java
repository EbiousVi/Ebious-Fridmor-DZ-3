package ru.liga.model;

import lombok.RequiredArgsConstructor;
import ru.liga.Dto.ProfileDto;
import ru.liga.Dto.SearchProfileDto;

import java.util.List;

@RequiredArgsConstructor
public class  UserProfileList {

    private final List<ProfileDto> userProfileList;
    private int idx = 0;

    public ProfileDto getCurrent() {
        return userProfileList.get(idx);
    }

    public ProfileDto getNext() {
        idx = isLast() ? 0 : idx + 1;
        return userProfileList.get(idx);
    }

    public ProfileDto getPrevious() {
        idx = isFirst() ? userProfileList.size() - 1 : idx - 1;
        return userProfileList.get(idx);
    }

    private boolean isFirst() {
        return idx == 0;
    }

    private boolean isLast() {
        return idx == userProfileList.size() - 1;
    }

    public boolean isEmpty() {
        return userProfileList.isEmpty();
    }
}
