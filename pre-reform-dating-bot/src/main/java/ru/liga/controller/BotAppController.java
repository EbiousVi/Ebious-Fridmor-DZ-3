package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.DatingBot;

@RestController
@RequiredArgsConstructor
public class BotAppController {
    private final DatingBot telegramBot;

    @PostMapping("/")
    public void onUpdateReceived(@RequestBody Update update) {
        telegramBot.onUpdateReceived(update);
    }
}
