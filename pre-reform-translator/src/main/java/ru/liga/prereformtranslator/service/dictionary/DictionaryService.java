package ru.liga.prereformtranslator.service.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictionaryService {

    private final CsvReader csvReader;
    private Map<String, String> dictionary;

    @Autowired
    public DictionaryService(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    @PostConstruct
    public void initDictionary() {
        Map<String, String> csv = null;
        try {
            csv = csvReader.readDataSetFromCsv()
                    .stream()
                    .collect(Collectors.toMap(Dictionary::getKey, Dictionary::getValue));

        } catch (Exception e) {
            e.printStackTrace();
        }
        dictionary = Collections.unmodifiableMap(csv);
    }

    public String getMask(String key) {
        return dictionary.getOrDefault(key, "");
    }

    public Boolean contains(String key) {
        return dictionary.containsKey(key);
    }
}
