package ru.liga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.liga.Bot;
import ru.liga.botapi.TelegramFacade;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class BotAppConfig {
    private String botUsername;
    private String botToken;

    @Bean
    public Bot Bot(TelegramFacade telegramFacade) {
        Bot bot = new Bot(telegramFacade);

        bot.setBotUsername(botUsername);
        bot.setBotToken(botToken);

        return bot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
