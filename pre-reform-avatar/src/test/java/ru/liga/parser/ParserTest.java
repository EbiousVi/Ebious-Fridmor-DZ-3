package ru.liga.parser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.domain.Description;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ParserTest {

    @Autowired
    Parser parser;

    @Test
    void parseDescription() {
        Description description = parser.parseDescription("Как дела?");
        System.out.println(description);
    }
}