package ru.liga.prereformtranslator.service.transaltor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.domain.Description;
import ru.liga.prereformtranslator.service.handler.Handler;
import ru.liga.prereformtranslator.service.parser.Parser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Translator implements TranslatorI {

    private static final String WHITESPACE = " ";
    private static final String SEPARATOR = "\\s+";
    private final List<Handler> handlers;
    private final Parser parser;

    @Override
    public Description translateDescription(String text) {
        Description description = parser.parseDescription(text);
        return new Description(translate(description.getTittle()), translate(description.getBody()));
    }

    @Override
    public String translateText(String description) {
        return translate(description);
    }

    private String translate(String text) {
        List<String> words = splitByWords(text);
        StringBuilder sb = new StringBuilder();
        for (String token : words) {
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
