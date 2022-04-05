package ru.liga.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class OpenCsvService {
    private static final Path PATH = Paths.get("pre-reform-dating-bot", "user_token.csv");
    private static final String[] header = {"id", "accessToken", "refreshToken"};

    public void writeData(long userId, String accessToken, String refreshToken) {
        File csvFile = PATH.toFile();
        if (!csvFile.exists()) {
            createCsv();
        }
        List<String[]> strings = readData();
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            writer.writeAll(strings);
            writer.writeNext(new String[]{String.valueOf(userId), accessToken, refreshToken});
        } catch (IOException e) {
            log.error(e.getMessage() + " in {} class", OpenCsvService.class.getSimpleName());
        }
    }

    public List<String[]> readData() {
        File csvFile = PATH.toFile();
        if (!csvFile.exists()) {
            createCsv();
        }
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            log.error(e.getMessage() + " in {} class", OpenCsvService.class.getSimpleName());
            return null;
        }
    }

    private void createCsv() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(PATH.toFile()))) {
            writer.writeNext(header);
        } catch (IOException e) {
            log.error(e.getMessage() + " in {} class", OpenCsvService.class.getSimpleName());
        }
    }
}
