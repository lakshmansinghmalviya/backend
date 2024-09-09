package com.example.quizapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllowedPaths {
	Logger log = LoggerFactory.getLogger(AllowedPaths.class);
	{
		log.info("The request came in the allowed paths got here is ");
	}
	public static final String[] PERMITTEDPATHS = 
		{ 
			"/api/auth/register",
			"/api/auth/login",
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/v3/api-docs/**",
			"/v2/api-docs",
			"/v3/api-docs",
			"/users/top",
			"/users/byRole/**",
	  };
}
