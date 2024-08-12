package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterUser {
	private String name;
	private String username;
	private String password;
	private String role;
	private String profilePic;
	private String bio;
}