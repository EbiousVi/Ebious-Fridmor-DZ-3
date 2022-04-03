package ru.liga.prereformtranslator.service.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Пишется в конце всякого слова, оканчивающегося на согласную: столъ, телефонъ, Санктъ-Петербургъ.
 * Это касается и слов с шипящими согласными в конце: мячъ, ужъ замужъ невтерпежъ.
 * Исключение — слова, оканчивающиеся на «и краткое»: й считался гласной.
 * В тех словах, где мы сейчас пишем на конце мягкий знак, в дореформенной орфографии тоже нужен он: олень, мышь, сидишь.
 */
@Service
@Order(3)
public class SolidSignHandler implements Handler {

    private static final Pattern CONSONANT_AT_THE_END = Pattern.compile("[бвгджзклмнпрстфхцчшщ]$");
    private static final String END_OF_WORD_PATTERN = "ь$";
    private static final String SOFT_SIGN = "ь";
    private static final String SOLID_SIGN = "ъ";

    @Override
    public String handle(String token) {
        return addSolidSignAtTheEndAfterConsonant(replaceSoftSignAtTheEnd(token));
    }

    private String addSolidSignAtTheEndAfterConsonant(String token) {
        Matcher matcher = CONSONANT_AT_THE_END.matcher(token);
        StringBuilder sb = new StringBuilder(token);
        while (matcher.find()) {
            sb.append(SOLID_SIGN);
        }
        return sb.toString();
    }

    private String replaceSoftSignAtTheEnd(String token) {
        if (token.endsWith(SOFT_SIGN)) {
            return token.replaceAll(END_OF_WORD_PATTERN, SOLID_SIGN);
        }
        return token;
    }
}
