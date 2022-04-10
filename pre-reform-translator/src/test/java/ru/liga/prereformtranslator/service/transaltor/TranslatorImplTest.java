package ru.liga.prereformtranslator.service.transaltor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class TranslatorImplTest {

    @Autowired
    TranslatorImpl translatorImpl;

    @Test
    void translateToString() {
        String test1 = "Великий обед Феофана был едой бедной Афины чьё сияние приехало в дом бедой надеяться лесничего медведи не ели";
        String translateTest1 = translatorImpl.translate(test1);
        System.out.println(test1);
        System.out.println(translateTest1);
    }

    @Test
    void translateToString2() {
        String test1 = "Великий обед \nФеофана был едой бедной\n Афины чьё сияние приехало \nв дом бедой надеяться лесничего медведи не ели";
        String translateTest1 = translatorImpl.translate(test1);
        System.out.println(test1);
        System.out.println(translateTest1);
    }

    @Test
    void translateToString3() {
        String test1 = "  \n  Славные были времена\n" +
                "    у \nэтого к\nазино \n" +
                "  \t  медв\nед весь мед ел\n " +
                " Федя";
        System.out.println(Arrays.toString(test1.split("\\h+")));
        String translateTest1 = translatorImpl.translate(test1);
        System.out.println(translateTest1.replaceAll("\\s+", " "));

        String test2 = "Славные были времена у этого казино медвед весь мед ел Федя";
        String translateTest2 = translatorImpl.translate(test2);
        System.out.println(translateTest2);
    }
}