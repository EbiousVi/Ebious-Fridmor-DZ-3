package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.Domain;
import ru.liga.service.FormCreation;

import java.awt.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final FormCreation formCreation;

    @PostMapping("/getImage")
    public byte[] getImage(@RequestBody Domain description) throws IOException, FontFormatException {
        return formCreation.execute(description);
    }
}
