package com.pooju.quizlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuizletApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizletApplication.class, args);
	}

}
