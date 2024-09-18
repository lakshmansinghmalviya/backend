package com.example.quizapp.config;


public class AllowedPaths {
 
	public static final String[] PERMITTEDPATHS = 
		{ 
			"/api/auth/register",
			"/api/auth/login",
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/v3/api-docs/**",
			"/users/educators/**",
			"/quizzes/top"
		};
}
