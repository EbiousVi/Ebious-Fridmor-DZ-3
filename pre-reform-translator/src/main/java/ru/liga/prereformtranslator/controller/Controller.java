package ru.liga.prereformtranslator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prereformtranslator.service.transaltor.TranslatorImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {

    private final TranslatorImpl translatorImpl;

    @PostMapping("translator/text")
    public String translateText(@RequestBody String text) {
        return translatorImpl.translate(text);
    }
}
