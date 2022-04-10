package ru.liga.prereformtranslator.service.transaltor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.handler.Handler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslatorImpl implements Translator {

    private static final String WHITESPACE = " ";
    private static final String HORIZONTAL_WHITESPACES = "\\h+";
    private static final String LINE_SEPARATOR = "\n";
    private static final String WRAP_LINE_SEPARATOR = " \n ";

    private final List<Handler> handlers;

    @Override
    public String translate(String text) {
        List<String> words = getWords(wrapLineBreak(text));
        StringBuilder sb = new StringBuilder();
        for (String token : words) {
            for (Handler handler : handlers) {
                token = handler.handle(token);
            }
            sb.append(token).append(WHITESPACE);
        }
        sb.deleteCharAt(sb.lastIndexOf(WHITESPACE));
        return sb.toString();
    }

    private List<String> getWords(String text) {
        return List.of(text.split(HORIZONTAL_WHITESPACES));
    }

    private String wrapLineBreak(String text) {
        return text.replaceAll(LINE_SEPARATOR, WRAP_LINE_SEPARATOR);
    }
}
