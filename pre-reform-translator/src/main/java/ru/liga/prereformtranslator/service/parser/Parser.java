package ru.liga.prereformtranslator.service.parser;

import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.domain.Description;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Parser {

    private static final int TITTLE_LENGTH_LIMIT = 32;
    private static final String WHITESPACE = " ";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HORIZONTAL_WHITESPACES = "\\h+";
    private static final String ALL_WHITESPACE = "\\s+";
    private static final String FIRST_WORD = "^[а-яА-Яa-zA-Z]{1,32},?";
    private static final String TITLE = "Title";

    public Description parseDescription(String text) {
        String replace = text.replaceAll(HORIZONTAL_WHITESPACES, WHITESPACE).trim();
        int firstIdx = replace.indexOf(LINE_SEPARATOR);
        if (firstIdx >= 1 && firstIdx <= TITTLE_LENGTH_LIMIT) {
            return new Description(
                    replace.substring(0, firstIdx).trim(),
                    replace.substring(firstIdx).trim()
            );
        }
        return getDescriptionTitle(replace);
    }

    private Description getDescriptionTitle(String text) {
        String replace = text.replaceAll(ALL_WHITESPACE, WHITESPACE).trim();
        Pattern compile = Pattern.compile(FIRST_WORD);
        Matcher matcher = compile.matcher(replace);
        if (matcher.find()) {
            String group = matcher.group();
            return new Description(group, replace.substring(group.length()));
        } else {
            return new Description(TITLE, replace);
        }
    }
}
