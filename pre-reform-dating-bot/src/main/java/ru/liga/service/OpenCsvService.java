package ru.liga.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class OpenCsvService {
    private static final Path PATH = Paths.get("storage", "pre-reform-dating-bot", "tokens", "user_token.csv");
    private static final String[] header = {"id", "accessToken", "refreshToken"};

    public void writeData(long userId, String accessToken, String refreshToken) throws IOException {
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
            throw new IOException("Failed to write data in csv");
        }
    }

    public List<String[]> readData() throws IOException {
        File csvFile = PATH.toFile();
        if (!csvFile.exists()) {
            createCsv();
        }
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            log.error(e.getMessage() + " in {} class", OpenCsvService.class.getSimpleName());
            throw new IOException("Failed to read data from csv");
        }
    }

    private void createCsv() throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(PATH.toFile()))) {
            writer.writeNext(header);
        } catch (IOException e) {
            log.error(e.getMessage() + " in {} class", OpenCsvService.class.getSimpleName());
            throw new IOException("Failed to create csv");
        }
    }
}
