package ru.liga.prereformtranslator.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.liga.prereformtranslator.service.transaltor.Translator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {

    private final Translator translator;
    private final RestTemplate restTemplate;

    @Autowired
    public Controller(Translator translator, RestTemplate restTemplate) {
        this.translator = translator;
        this.restTemplate = restTemplate;
    }

    //REST Resource Naming Guide
    @PostMapping("/translate")
    public String translate(@RequestBody String text) {
        return translator.translate(text);
    }

    @GetMapping("/translate")
    public void foo() throws IOException {
        NewProfileDto newProfileDto = new NewProfileDto(104_000L, "U_100_000", "U_100_000", Sex.MALE, List.of(Sex.MALE.name));
        HttpEntity<NewProfileDto> req = new HttpEntity<>(newProfileDto);
        CreateProfileDto createProfileDto = restTemplate.postForObject("http://localhost:6064/dating-server/profiles/", req, CreateProfileDto.class);
        FileUtils.writeByteArrayToFile(new File("asd.jpg"), createProfileDto.getImage());
    }
}
