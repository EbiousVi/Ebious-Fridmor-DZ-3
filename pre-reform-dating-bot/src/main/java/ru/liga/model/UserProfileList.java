package ru.liga.model;

import ru.liga.Dto.ProfileDto;

import java.util.LinkedList;
import java.util.List;

public class  UserProfileList {

    private final List<ProfileDto> userProfileList;
    private int idx = 0;

    public UserProfileList(LinkedList<ProfileDto> userProfileList) {
        this.userProfileList = userProfileList;
    }

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

    public void removeCurrent() {
        userProfileList.remove(idx);
        idx = isFirst() ? userProfileList.size() - 1 : idx - 1;
    }

    public boolean isEmpty() {
        return userProfileList.isEmpty();
    }
}
