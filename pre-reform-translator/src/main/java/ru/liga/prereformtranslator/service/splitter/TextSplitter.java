package ru.liga.prereformtranslator.service.splitter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextSplitter {

    private static final String SEPARATOR = "\\s+";

    public List<String> splitByWords(String text) {
        return List.of(text.split(SEPARATOR));
    }
}
