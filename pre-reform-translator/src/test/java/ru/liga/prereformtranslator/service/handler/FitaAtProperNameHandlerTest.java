package ru.liga.prereformtranslator.service.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FitaAtProperNameHandlerTest {

    private final Handler fitaName = new FitaAtProperNameHandler();

    @Test
    @DisplayName("< ѳ > в середине слова, 1 замена")
    void replaceRuFtoFitaAtProperName() {
        String expected = "Аѳанасий";
        String res = fitaName.handle("Афанасий");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< Ѳ > в начале слова, 1 замена")
    void replaceRuFtoFitaAtProperNameFistCharacter() {
        String expected = "Ѳедор";
        String res = fitaName.handle("Федор");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< ѳ > в середине и начале слова, 2 замены")
    void replaceRuFtoFitaAtProperNameMultiMatch() {
        String expected = "Ѳеоѳан";
        String res = fitaName.handle("Феофан");
        assertThat(res).isEqualTo(expected);
    }
}