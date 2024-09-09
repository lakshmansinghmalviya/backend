package com.example.quizapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quizapp.config.WebConfig;

@SpringBootApplication
public class QuizappApplication {
	static Logger log = LoggerFactory.getLogger(QuizappApplication.class);

	public static void main(String[] args) {
		log.info("The request came in the main method got here is");
		SpringApplication.run(QuizappApplication.class, args);
	}
}
