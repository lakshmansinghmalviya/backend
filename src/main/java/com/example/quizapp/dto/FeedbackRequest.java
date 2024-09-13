package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

	@NotBlank(message = "Feedback text should not be empty or null.")
	@Size(min= 3, max = 100, message = "Feedback text should not exceed 100 characters and at least 3 ")
	private String feedbackText;

	@NotNull(message = "Question ID should not be null.")
	private Long questionId;
}
