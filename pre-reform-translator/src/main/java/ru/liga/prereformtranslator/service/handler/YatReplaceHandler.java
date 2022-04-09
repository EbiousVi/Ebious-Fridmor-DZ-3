package ru.liga.prereformtranslator.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.dictionary.DictionaryService;
import ru.liga.prereformtranslator.service.stem.StemmerPorter;

@Service
@RequiredArgsConstructor
@Order(1)
public class YatReplaceHandler implements Handler {

    private final StemmerPorter stemmerPorter;
    private final DictionaryService dictionary;

    @Override
    public String handle(String token) {
        String stem = stemmerPorter.stem(token);
        if (dictionary.contains(stem)) {
            return token.replace(stem, dictionary.getPattern(stem));
        } else {
            return token;
        }
    }
}
