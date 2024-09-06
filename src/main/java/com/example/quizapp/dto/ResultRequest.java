package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultRequest {

	@NotNull(message = "score should not be null ")
	private Long score;

	@NotNull(message = "totalScore should not be null ")
	private Long totalScore;

	@NotNull(message = "timeSpent should not be null ")
	private Long timeSpent;

	@NotNull(message = "isCompleted should not be null ")
	private Boolean isCompleted;

	@NotNull(message = "correctAnswers should not be null ")
	private Long correctAnswers;

	@NotNull(message = "incorrectAnswers should not be null ")
	private Long incorrectAnswers;

	@NotNull(message = "totalQuestion should not be null ")
	private Long totalQuestion;

	@NotNull(message = "timesTaken should not be null ")
	private Long timesTaken;

	@NotNull(message = "userId should not be null ")
	private Long userId;

	@NotNull(message = "QuizId should not be null")
	private Long quizId;
}