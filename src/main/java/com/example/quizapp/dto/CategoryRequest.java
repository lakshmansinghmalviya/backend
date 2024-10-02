package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

	@NotBlank(message = "Name is required.")
	@Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$", message = "Name should start with a letter and contain only letters, numbers, and spaces.")
	private String name;

	@NotBlank(message = "Description is required.")
	@Size(min = 10, max = 500, message = "Description should have between 10 and 500 characters.")
	private String description;

	private String categoryPic;
}
