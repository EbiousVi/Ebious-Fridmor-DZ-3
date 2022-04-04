package ru.liga.model;

import lombok.RequiredArgsConstructor;
import ru.liga.Dto.SearchProfileDto;

import java.util.List;

@RequiredArgsConstructor
public class UserProfileList {

    private final List<?> userProfileList;
    private int idx = 0;

    public Object getCurrent() {
        return userProfileList.get(idx);
    }

    public Object getNext() {
        idx = isLast() ? 0 : idx + 1;
        return userProfileList.get(idx);
    }

    public Object getPrevious() {
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
