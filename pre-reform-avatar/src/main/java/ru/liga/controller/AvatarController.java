package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.parser.Parser;
import ru.liga.service.AvatarCreation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AvatarController {

    private final AvatarCreation avatarCreation;
    private final Parser parser;

    @PostMapping("/avatar")
    public byte[] createAvatar(@RequestBody String text) {
        System.out.println(parser.parseDescription(text));
        return avatarCreation.execute(parser.parseDescription(text));
    }
}
