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
    private static final String FIRST_WORD = "^[а-яА-Яa-zA-Z]{1,32}";
    private static final String TITLE = "Title";

    public Description parseDescription(String text) {
        String replace = text.replaceAll(HORIZONTAL_WHITESPACES, WHITESPACE).trim();
        int firstLineSeparatorIdx = replace.indexOf(LINE_SEPARATOR);
        if (firstLineSeparatorIdx >= 1 && firstLineSeparatorIdx <= TITTLE_LENGTH_LIMIT) {
            return new Description(
                    replace.substring(0, firstLineSeparatorIdx).trim(),
                    replace.substring(firstLineSeparatorIdx).trim()
            );
        }
        return getDescriptionFirstWordTitle(replace);
    }

    private Description getDescriptionFirstWordTitle(String text) {
        String replace = text.replaceAll(ALL_WHITESPACE, WHITESPACE).trim();
        Pattern compile = Pattern.compile(FIRST_WORD);
        Matcher matcher = compile.matcher(replace);
        if (matcher.find()) {
            String group = matcher.group();
            return new Description(matcher.group().trim(), replace.substring(group.length()).trim());
        } else {
            return new Description(TITLE, replace);
        }
    }
}
