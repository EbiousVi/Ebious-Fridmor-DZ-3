package ru.liga.prereformtranslator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformtranslator.service.transaltor.Translator;

@RestController
public class Controller {

    private final Translator translator;

    @Autowired
    public Controller(Translator translator) {
        this.translator = translator;
    }

    @PostMapping("/translate")
    public String translate(@RequestBody String text) {
        return translator.translate(text);
    }
}
