package com.youngchayoungcha.tastynote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TastynoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(TastynoteApplication.class, args);
	}
}
