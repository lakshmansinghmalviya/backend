package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	private String message;
	private String token;
	private Long userId;
	private String role;
	private Integer status;
}
