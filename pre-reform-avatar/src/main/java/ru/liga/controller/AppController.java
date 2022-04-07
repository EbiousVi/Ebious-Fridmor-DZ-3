package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.domain.Description;
import ru.liga.service.FormCreation;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final FormCreation formCreation;

    @PostMapping("/avatar")
    public byte[] getAvatar(@RequestBody Description description) {
        return formCreation.execute(description);
    }
}
