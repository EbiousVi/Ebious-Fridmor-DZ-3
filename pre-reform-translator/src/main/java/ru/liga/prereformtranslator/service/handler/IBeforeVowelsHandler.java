package ru.liga.prereformtranslator.service.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Его нужно писать на месте нынешнего и, если сразу после него идет другая гласная буква
 * (в том числе — по дореволюционным правилам — й): линія, другіе, пріѣхалъ, синій
 */
@Service
@Order(2)
public class IBeforeVowelsHandler implements Handler {

    private static final Pattern PATTERN = Pattern.compile("[и][ауоыийэяюёе]");
    private static final String REPLACEABLE = "и";
    private static final String REPLACEMENT = "i";

    @Override
    public String handle(String token) {
        Matcher matcher = PATTERN.matcher(token);
        while (matcher.find()) {
            String replace = matcher.group().replaceFirst(REPLACEABLE, REPLACEMENT);
            token = token.replace(matcher.group(), replace);
        }
        return token;
    }
}
