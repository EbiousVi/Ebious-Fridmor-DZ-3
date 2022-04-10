package ru.liga.prereformtranslator.service.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.dictionary.Entry;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public List<Entry> readDataSetFromCsv() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(dictionary.getInputStream(), StandardCharsets.UTF_8))) {
            return bufferedReader
                    .lines()
                    .skip(TITLE_ROW_INDEX)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can not read dictionary", e);
            throw new ReaderException("Can not read csv file!");
        }
    }

    private Entry parseLine(String line) {
        String[] split = line.split(CSV_SEPARATOR);
        return new Entry(split[KEY_INDEX], split[VALUE_INDEX]);
    }
}

