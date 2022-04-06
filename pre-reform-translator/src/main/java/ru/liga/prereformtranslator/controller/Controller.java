package ru.liga.prereformtranslator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformtranslator.service.domain.Domain;
import ru.liga.prereformtranslator.service.transaltor.Translator;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final Translator translator;

    @PostMapping("/translate-string")
    public String translateToString(@RequestBody String text) {
        return translator.translateToString(text);
    }

    @PostMapping("/translate-object")
    public Domain translateToObject(@RequestBody String text) {
        return translator.translateToObject(text);
    }
}
