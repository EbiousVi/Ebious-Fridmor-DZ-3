package ru.liga.prereformtranslator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static void main(String[] args) {
        Pattern save = Pattern.compile("^\\w+\\s+\\n");
        String text = "Hello     \n hello";
        Matcher matcher = save.matcher(text);
        if (matcher.matches()) {
        } else {

        }
    }
}
