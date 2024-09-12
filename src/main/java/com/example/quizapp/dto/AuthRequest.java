package com.example.quizapp.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthRequest {
	@Size(min = 3, max = 100, message = "Username should have at least 3 characters and max 100")
	private String username;

	@Size(min = 8, message = "Password should have at least 8 characters")
	private String password;
}