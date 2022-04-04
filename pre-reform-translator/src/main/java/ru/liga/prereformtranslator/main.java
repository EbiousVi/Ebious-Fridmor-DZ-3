package ru.liga.prereformtranslator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static void main(String[] args) {

        Pattern save = Pattern.compile("^(.*)$");
        String text = "Hello\nhello";
        Matcher matcher = save.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.start());
        }
    }
}
