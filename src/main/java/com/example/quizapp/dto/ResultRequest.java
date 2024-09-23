package com.example.quizapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultRequest {

	@NotNull(message = "Score should not be null.")
	@Min(value = 0, message = "Score must be at least 0.")
	private Long score;

	@NotNull(message = "Total score should not be null.")
	@Min(value = 0, message = "Total score must be at least 0.")
	private Long totalScore;

	@NotNull(message = "Time spent should not be null.")
	@Min(value = 0, message = "Time spent must be at least 0 seconds.")
	private Long timeSpent;

	@NotNull(message = "Completion status should not be null.")
	private Boolean isCompleted;

	@NotNull(message = "Number of correct answers should not be null.")
	@Min(value = 0, message = "Number of correct answers must be at least 0.")
	private Long correctAnswers;

	@NotNull(message = "Number of incorrect answers should not be null.")
	@Min(value = 0, message = "Number of incorrect answers must be at least 0.")
	private Long incorrectAnswers;

	@NotNull(message = "Total number of questions should not be null.")
	@Min(value = 0, message = "Total number of questions must be at least 0.")
	private Long totalQuestion;

	@NotNull(message = "Number of times taken should not be null.")
	@Min(value = 0, message = "Number of times taken must be at least 0.")
	private Long timesTaken;

	@NotNull(message = "Quiz ID should not be null.")
	private Long quizId;
}
