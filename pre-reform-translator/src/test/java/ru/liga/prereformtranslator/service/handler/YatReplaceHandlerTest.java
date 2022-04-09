package ru.liga.prereformtranslator.service.handler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class YatReplaceHandlerTest {

    @Autowired
    YatReplaceHandler yatHandler;

    @Test
    void yatHandlerCase1() {
        String expected = "человѣк";
        String res = yatHandler.handle("человек");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    void yatHandlerCase2() {
        String expected = "бѣг";
        String res = yatHandler.handle("бег");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    void yatHandlerCase3() {
        String expected = "великолѣпный";
        String res = yatHandler.handle("великолепный");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    void yatHandlerCase4() {
        String expected = "бѣдной";
        String res = yatHandler.handle("бедной");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    void yatHandlerCase5() {
        String expected = "бѣдой";
        String res = yatHandler.handle("бедой");
        assertThat(res).isEqualTo(expected);
    }
}
