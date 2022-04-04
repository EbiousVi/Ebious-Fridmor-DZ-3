package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.service.FormCreation;

import java.awt.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final FormCreation formCreation;

    @GetMapping("/getImage/{text}")
    public byte[] getImage(@PathVariable("text") String description) throws IOException, FontFormatException {
        return formCreation.execute(description);
    }
}
