package ru.liga.prereformtranslator.service.dictionary;

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
public class CsvReader {


    private static final int TITLE_ROW_INDEX = 1;
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
            throw new ReaderException("Can not read csv file!");
        }
    }

    private Dictionary parseLine(String line) {
        String[] split = line.split(";");
        return new Dictionary(split[0], split[1]);
    }
}

