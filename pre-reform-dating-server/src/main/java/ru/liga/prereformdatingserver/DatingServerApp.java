package ru.liga.prereformdatingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class DatingServerApp {

    public static void main(String[] args) {
        SpringApplication.run(DatingServerApp.class, args);
    }
}
