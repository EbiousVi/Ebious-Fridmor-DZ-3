package ru.liga.prereformtranslator.service.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.prereformtranslator.service.domain.Description;


@ExtendWith(MockitoExtension.class)
class ParserTest {

    @InjectMocks
    Parser parser;

    @Test
    void parseDescription() {
        Description description = parser.parseDescription("привет\n фвшныа рфтыводфы вфывфыв");
        Assertions.assertThat(description.getTittle()).isEqualTo("привет");
    }

    @Test
    void parseDescriptionTitleLimit() {
        Description description = parser.parseDescription("СпрингимеетсобственнуюMVCплатформувебприложенийкотораянебыла первоначальнозапланирована");
        Assertions.assertThat(description.getTittle().length()).isEqualTo(32);
    }

    @Test
    void parseDescriptionMultipleLineSeparatorAtStart() {
        Description description = parser.parseDescription("\n\n\n\n\nСпрингимеетсобственнуюMVCплатформувебприложенийкотораянебыла первоначальнозапланирована");
        Assertions.assertThat(description.getTittle().length()).isEqualTo(32);
    }
}