package ru.liga.prereformdatingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PreReformDatingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreReformDatingServerApplication.class, args);
	}
}
