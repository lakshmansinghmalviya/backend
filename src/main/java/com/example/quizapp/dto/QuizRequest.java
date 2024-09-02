package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {

	@Size(min = 3, message = "Title should have at least 3 characters")
	private String title;

	@Size(min = 6, message = "description should have at least 6 characters")
	private String description;

	private String quizPic;

	@NotNull(message = "There should be some time for every quiz")
	private Long timeLimit;

	private Boolean randomizeQuestions;

	@NotNull(message = "The categoryId should not be null")
	private Long categoryId;

	@NotNull(message = "The creatorId should not be null")
	private Long creatorId;
}
