package ru.liga.model;

import ru.liga.dto.ProfileDto;

import java.util.LinkedList;
import java.util.List;

public class UserSuggestionList {

    private final List<ProfileDto> userSuggestionList;
    private int idx = 0;

    public UserSuggestionList(LinkedList<ProfileDto> userSuggestionList) {
        this.userSuggestionList = userSuggestionList;
    }

    public ProfileDto getCurrent() {
        return userSuggestionList.get(idx);
    }

    public ProfileDto getNext() {
        idx = isLast() ? 0 : idx + 1;
        return userSuggestionList.get(idx);
    }

    public ProfileDto getPrevious() {
        idx = isFirst() ? userSuggestionList.size() - 1 : idx - 1;
        return userSuggestionList.get(idx);
    }

    private boolean isFirst() {
        return idx == 0;
    }

    private boolean isLast() {
        return idx == userSuggestionList.size() - 1;
    }

    public void removeCurrent() {
        userSuggestionList.remove(idx);
        idx = isFirst() ? userSuggestionList.size() - 1 : idx - 1;
    }

    public boolean isEmpty() {
        return userSuggestionList.isEmpty();
    }
}
