package ru.liga.prereformtranslator.service.transaltor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TranslatorTest {

    @Autowired
    Translator translator;

    @Test
    void translateChaosHandlerCase() {
        String test1 = "Великий обед Феофана был едой бедной Афины чьё сияние приехало в дом бедой надеяться лесничего медведи не ели";
        String translateTest1 = translator.translate(test1);
        System.out.println(test1);
        System.out.println(translateTest1);

        String test2 = "«Хотел бы найти совершенно не закомплексованную, разумную, адекватную во взглядах на жизнь, свободолюбивую девушку, без тараканов в голове и каких-либо заморочек. Реалистку и оптимистку. Веселую и общительную, которая «живет здесь и сейчас».";
        String translateTest2 = translator.translate(test2);
        System.out.println(test2);
        System.out.println(translateTest2);
    }
}