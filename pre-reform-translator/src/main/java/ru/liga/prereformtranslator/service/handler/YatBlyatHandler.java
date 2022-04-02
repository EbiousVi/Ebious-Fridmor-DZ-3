package ru.liga.prereformtranslator.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.dictionary.DictionaryService;
import ru.liga.prereformtranslator.service.stem.StemmerPorter;

@Service
@Order(1)
public class YatBlyatHandler implements Handler {

    private final StemmerPorter stemmerPorter;
    private final DictionaryService dictionary;

    @Autowired
    public YatBlyatHandler(StemmerPorter stemmerPorter, DictionaryService dictionary) {
        this.stemmerPorter = stemmerPorter;
        this.dictionary = dictionary;
    }

    @Override
    public String handle(String token) {
        String stem = stemmerPorter.stem(token);
        if (dictionary.contains(stem)) {
            return token.replace(stem, dictionary.getMask(stem));
        } else {
            return token;
        }
    }
}
