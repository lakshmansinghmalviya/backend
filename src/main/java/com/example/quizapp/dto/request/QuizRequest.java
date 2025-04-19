package com.example.quizapp.dto.request;

import com.example.quizapp.enums.Severity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {

	@NotBlank(message = "title is required.")
	@Size(min = 3, max = 100, message = "title should have between 3 and 100 characters.")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$", message = "title should start with a letter and contain only letters, numbers, and spaces.")
	private String title;

	@NotBlank(message = "Description is required.")
	@Size(min = 10, max = 500, message = "Description should have between 10 and 500 characters.")
	private String description;

	private String quizPic;

	@NotNull(message = "Time limit should not be null.")
	@Min(value = 1, message = "Time limit should be greater than 0.")
	private Long timeLimit;

	private Boolean randomizeQuestions;

	private Long categoryId;

	@NotNull(message = "Severity should not be null.")
	private Severity severity;
}
