package ru.liga.prereformtranslator.service.dictionary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.reader.CsvReader;
import ru.liga.prereformtranslator.service.reader.ReaderException;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DictionaryService {

    private final CsvReader csvReader;
    private Map<String, String> dictionary;

    @PostConstruct
    public void initDictionary() {
        try {
            Map<String, String> init = csvReader.readDataSetFromCsv()
                    .stream()
                    .collect(Collectors.toMap(Dictionary::getKey, Dictionary::getValue));
            dictionary = Collections.unmodifiableMap(init);
        } catch (ReaderException e) {
            log.error("App init failed! Can not init translator!", e);
            System.exit(-1);
        }
    }

    public String getMask(String key) {
        return dictionary.getOrDefault(key, "");
    }

    public Boolean contains(String key) {
        return dictionary.containsKey(key);
    }
}
