package ru.liga.prereformtranslator.service.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Употребляется вместо нынешнего ф в Именах собственных: Агаѳья, Анѳимъ, Аѳанасій, Аѳина, Варѳоломей
 */
@Service
@Order(4)
public class FitaReplaceHandler implements Handler {

    private static final Pattern PATTERN = Pattern.compile("\\s*[А-Я][а-я]+\\s*");
    private static final String REPLACEABLE = "ф";
    private static final String REPLACEMENT = "ѳ";

    @Override
    public String handle(String token) {
        if (isProperName(token)) {
            return replaceChar(token);
        }
        return token;
    }

    private String replaceChar(String token) {
        if (token.startsWith(REPLACEABLE.toUpperCase())) {
            token = token.replace(REPLACEABLE.toUpperCase(), REPLACEMENT.toUpperCase());
        }
        return token.replace(REPLACEABLE, REPLACEMENT);
    }

    private Boolean isProperName(String token) {
        return PATTERN.matcher(token).matches();
    }
}
