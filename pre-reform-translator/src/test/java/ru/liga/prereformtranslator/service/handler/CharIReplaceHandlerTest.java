package ru.liga.prereformtranslator.service.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CharIReplaceHandlerTest {

    @InjectMocks
    CharIReplaceHandler iBeforeVowels;

    @Test
    @DisplayName("< i > перед < й >, 1 замены")
    void iBeforeVowelsSingleMatch() {
        String expected = "синiй";
        String res = iBeforeVowels.handle("синий");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< i > перед < я > и < е >, 2 замены")
    void iBeforeVowelsDoubleMatch() {
        String expected = "сiянiе";
        String res = iBeforeVowels.handle("сияние");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< i > перед всеми согласными, 11 замен")
    void iBeforeVowelsAllMatch() {
        String expected = "сiазiупiокiырiилiймiэцiяфiюдiезiё";
        String res = iBeforeVowels.handle("сиазиупиокиыриилиймиэцияфиюдиезиё");
        assertThat(res).isEqualTo(expected);
    }
}