package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.domain.Description;
import ru.liga.service.AvatarCreation;

@RestController
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarCreation formCreation;

    @PostMapping("/avatar")
    public byte[] getAvatar(@RequestBody Description description) {
        return formCreation.execute(description);
    }
}
