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
    private final List<Handler> handlers;

    @Override
    public String translate(String text) {
        List<String> words = getWords(text);
        StringBuilder sb = new StringBuilder();
        for (String token : words) {
            for (Handler handler : handlers) {
                token = handler.handle(token);
            }
            sb.append(token).append(WHITESPACE);
        }
        sb.deleteCharAt(text.lastIndexOf(WHITESPACE));
        return sb.toString();
    }

    private List<String> getWords(String text) {
        return List.of(text.split(HORIZONTAL_WHITESPACES));
    }
}
