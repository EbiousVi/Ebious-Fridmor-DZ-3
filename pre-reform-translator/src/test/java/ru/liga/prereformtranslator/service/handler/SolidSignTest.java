package ru.liga.prereformtranslator.service.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SolidSignTest {

    private final Handler solidSign = new SolidSignHandler();

    @Test
    @DisplayName("< б > в конце, замена на < ъ >")
    void solidSignAtTheEndOfConsonantLetterRuB() {
        String expected = "баобабъ";
        String res = solidSign.handle("баобаб");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< в > в конце, замена на < ъ >")
    void solidSignAtTheEndOfConsonantLetterRuV() {
        String expected = "массивъ";
        String res = solidSign.handle("массив");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< г > в конце, замена на < ъ >")
    void solidSignAtTheEndOfConsonantLetterRuG() {
        String expected = "археологъ";
        String res = solidSign.handle("археолог");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("< ь > в конце, замена на < ъ >")
    void solidSignAtTheEndReplaceSoftSign() {
        String expected = "гулятъ";
        String res = solidSign.handle("гулять");
        assertThat(res).isEqualTo(expected);
    }

    @Test
    @DisplayName("множественный < ь > в слове, замена только последнего на < ъ >")
    void solidSignAtTheEndReplaceOnlyLastSoftSign() {
        String expected = "льнутъ";
        String res = solidSign.handle("льнуть");
        assertThat(res).isEqualTo(expected);
    }
}