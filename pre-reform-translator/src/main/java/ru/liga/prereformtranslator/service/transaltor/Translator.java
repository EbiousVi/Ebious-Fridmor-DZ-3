package ru.liga.prereformtranslator.service.transaltor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.handler.Handler;
import ru.liga.prereformtranslator.service.splitter.TextSplitter;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class Translator {

    private final List<Handler> handlers;
    private final TextSplitter splitter;

    @Autowired
    public Translator(List<Handler> handlers, TextSplitter splitter) {
        this.handlers = handlers;
        this.splitter = splitter;
    }

    public String translate(String text) {
        List<String> strings = splitter.splitByWords(text);
        StringBuilder sb = new StringBuilder();
        for (String token : strings) {
            for (Handler handler : handlers) {
                token = handler.handle(token);
            }
            sb.append(token).append(" ");
        }
        return sb.toString();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Handler order, does matter?");
        System.out.println(handlers);
    }
}
