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
public class SolidSignReplaceHandler implements Handler {

    private static final Pattern CONSONANT_AT_THE_END = Pattern.compile("[бвгджзклмнпрстфхцчшщ]\\s*$");
    private static final Pattern SOFT_SIGN_PATTERN = Pattern.compile("[ь]\\s*$");
    private static final String SOFT_SIGN = "ь";
    private static final String SOLID_SIGN = "ъ";

    @Override
    public String handle(String token) {
        return addSolidSign(replaceSoftSign(token));
    }

    private String addSolidSign(String token) {
        Matcher matcher = CONSONANT_AT_THE_END.matcher(token);
        StringBuilder sb = new StringBuilder(token);
        if (matcher.find()) {
            int i = token.indexOf(matcher.group());
            sb.insert(i + 1, SOLID_SIGN);
        }
        return sb.toString();
    }

    private String replaceSoftSign(String token) {
        Matcher matcher = SOFT_SIGN_PATTERN.matcher(token);
        if (matcher.find()) {
            int i = token.lastIndexOf(SOFT_SIGN);
            return token.substring(0, i) + SOLID_SIGN;
        }
        return token;
    }
}
