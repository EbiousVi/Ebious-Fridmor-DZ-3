package ru.liga.prereformtranslator.service.transaltor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prereformtranslator.service.domain.Domain;
import ru.liga.prereformtranslator.service.handler.Handler;
import ru.liga.prereformtranslator.service.splitter.TextSplitter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Translator {

    public static final int TITTLE_LIMIT = 32;
    public static final String WHITESPACE = " ";
    private final List<Handler> handlers;
    private final TextSplitter splitter;

    @Autowired
    public Translator(List<Handler> handlers, TextSplitter splitter) {
        this.handlers = handlers;
        this.splitter = splitter;
    }

    public Domain foo(String text) {
        String replace = text.replaceAll("\\h+", WHITESPACE);
        int firstLineSeparatorIndex = replace.indexOf("\n");
        if (firstLineSeparatorIndex >= 1 && firstLineSeparatorIndex <= TITTLE_LIMIT) {
            return new Domain(replace.substring(0, firstLineSeparatorIndex), replace.substring(firstLineSeparatorIndex + 1));
        } else {
            String replaceAll = replace.replaceAll("\\s+", WHITESPACE);
            Pattern compile = Pattern.compile("^[а-яА-Я]{1,24}");
            Matcher matcher = compile.matcher(replaceAll);
            if (matcher.find()) {
                String group = matcher.group();
                return new Domain(matcher.group(), replaceAll.substring(group.length()));
            } else {
                return new Domain("Title", text);
            }
        }
    }

    public Domain translateToObject(String text) {
        Domain foo = foo(text);
        return new Domain(translate(foo.getTittle()), translate(foo.getBody()));
    }

    public String translateToString(String text) {
        return translate(text);
    }

    private String translate(String text) {
        List<String> strings = splitter.splitByWords(text);
        StringBuilder sb = new StringBuilder();
        for (String token : strings) {
            for (Handler handler : handlers) {
                token = handler.handle(token);
            }
            sb.append(token).append(WHITESPACE);
        }
        return sb.toString();
    }
}
