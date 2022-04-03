package ru.liga.prereformtranslator.service.dictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.PreReformTranslatorApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvReader {

    private static final int TITLE_ROW_INDEX = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String CSV_SEPARATOR = ";";

    @Value("classpath:/dictionary/dictionary.csv")
    Resource dictionary;

    public List<Dictionary> readDataSetFromCsv() {
        try {
            return new BufferedReader(new InputStreamReader(dictionary.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .skip(TITLE_ROW_INDEX)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can read dictionary", e);
            throw new ReaderException("Can not read csv file!");
        }
    }

    private Dictionary parseLine(String line) {
        String[] split = line.split(CSV_SEPARATOR);
        return new Dictionary(split[KEY_INDEX], split[VALUE_INDEX]);
    }
}

