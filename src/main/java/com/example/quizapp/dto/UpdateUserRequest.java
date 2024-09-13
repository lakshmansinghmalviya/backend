package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequest {
	
	@NotBlank(message = "Name is required.")
    @Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.")
    private String name;
	
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 8, max = 50, message = "Password should have at least 8 characters and max 50 ")
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", 
	message = "Password must be at least 8 characters long and max 50 and include at least one number, one uppercase letter, and one lowercase letter.")
	private String password;
	private String education;
	

	private String profilePic;

	@Size(min = 10, max = 100, message = "Bio should have at least 10 characters and maximum 100 characters")
	private String bio;
}