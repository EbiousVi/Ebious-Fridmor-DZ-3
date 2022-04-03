package ru.liga.prereformtranslator.service.dictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DictionaryService {

    private final CsvReader csvReader;
    private Map<String, String> dictionary ;

    @Autowired
    public DictionaryService(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    @PostConstruct
    public void initDictionary() {
        try {
            Map<String, String> init = csvReader.readDataSetFromCsv()
                    .stream()
                    .collect(Collectors.toMap(Dictionary::getKey, Dictionary::getValue));
            dictionary = Collections.unmodifiableMap(init);
        } catch (ReaderException e) {
            log.error("Can not init translator!", e);
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
