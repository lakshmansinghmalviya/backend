package com.example.quizapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/test")
	public String testingEndPoint() {
		return "Testing the aws hosted application running.. congrats!!";
	}
}
