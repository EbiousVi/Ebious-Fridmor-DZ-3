package ru.liga.prereformtranslator.service.transaltor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.domain.Domain;
import ru.liga.prereformtranslator.service.handler.Handler;
import ru.liga.prereformtranslator.service.parser.Parser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Translator {

    private static final String WHITESPACE = " ";
    private static final String SEPARATOR = "\\s+";
    private final List<Handler> handlers;
    private final Parser parser;

    public Domain translateToObject(String text) {
        Domain foo = parser.parse(text);
        return new Domain(translate(foo.getTittle()), translate(foo.getBody()));
    }

    public String translateToString(String text) {
        return translate(text);
    }

    private String translate(String text) {
        List<String> strings = splitByWords(text);
        StringBuilder sb = new StringBuilder();
        for (String token : strings) {
            for (Handler handler : handlers) {
                token = handler.handle(token);
            }
            sb.append(token).append(WHITESPACE);
        }
        return sb.toString();
    }

    private List<String> splitByWords(String text) {
        return List.of(text.split(SEPARATOR));
    }
}
