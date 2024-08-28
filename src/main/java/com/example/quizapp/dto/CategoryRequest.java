package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
	
	@NotNull(message = "Creator id should not be null or blank")
	private Long creatorId;
	
	@Size(min = 3, message = "name should have at least 3 characters")
	private String name;
	
	@Size(min = 6, message = "Description should have at least 6 characters")
	private String description;
	
	private String categoryPic;
}
