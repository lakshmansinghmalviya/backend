package com.example.quizapp.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequest {
	@Size(min = 3, max = 100, message = "Username should have at least 3 characters and max 100")
	private String username;
	
	@Size(min = 3, max = 50, message = "Name should have at least 3 characters and max 50")
	private String name;
	
	@Size(min = 6, message = "Password should have at least 6 characters")
	private String password;
	
	private String profilePic;

	@Size(min = 10,max=100, message = "Bio should have at least 10 characters and maximum 100 characters")
	private String bio;
}