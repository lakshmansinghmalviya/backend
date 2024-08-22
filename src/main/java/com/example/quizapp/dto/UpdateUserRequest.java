package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequest {
	private String username;
	private String name;
	private String password;
	private String profilePic;
	private String role;
	private String bio;
} 