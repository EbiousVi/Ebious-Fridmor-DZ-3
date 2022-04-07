package ru.liga.prereformtranslator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformtranslator.service.domain.Description;
import ru.liga.prereformtranslator.service.transaltor.Translator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/translator")
public class Controller {

    private final Translator translator;

    @PostMapping("/text")
    public String translateText(@RequestBody String text) {
        return translator.translateText(text);
    }

    @PostMapping("/description")
    public Description translateDescription(@RequestBody String text) {
        return translator.translateDescription(text);
    }
}
