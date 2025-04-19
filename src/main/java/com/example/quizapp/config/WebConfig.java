package com.example.quizapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000", "https://quizzy-murex.vercel.app",
						"http://quizzy-murex.vercel.app", "http://quizzy-app.eu-north-1.elasticbeanstalk.com/")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true);
	}
}