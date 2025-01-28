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

	public ResultRequest(
			@NotNull(message = "Score should not be null.") @Min(value = 0, message = "Score must be at least 0.") Long score,
			@NotNull(message = "Total score should not be null.") @Min(value = 0, message = "Total score must be at least 0.") Long totalScore,
			@NotNull(message = "Time spent should not be null.") @Min(value = 0, message = "Time spent must be at least 0 seconds.") Long timeSpent,
			@NotNull(message = "Completion status should not be null.") Boolean isCompleted,
			@NotNull(message = "Number of correct answers should not be null.") @Min(value = 0, message = "Number of correct answers must be at least 0.") Long correctAnswers,
			@NotNull(message = "Number of incorrect answers should not be null.") @Min(value = 0, message = "Number of incorrect answers must be at least 0.") Long incorrectAnswers,
			@NotNull(message = "Total number of questions should not be null.") @Min(value = 0, message = "Total number of questions must be at least 0.") Long totalQuestion,
			@NotNull(message = "Number of times taken should not be null.") @Min(value = 0, message = "Number of times taken must be at least 0.") Long timesTaken,
			@NotNull(message = "Quiz ID should not be null.") Long quizId) {
		super();
		this.score = score;
		this.totalScore = totalScore;
		this.timeSpent = timeSpent;
		this.isCompleted = isCompleted;
		this.correctAnswers = correctAnswers;
		this.incorrectAnswers = incorrectAnswers;
		this.totalQuestion = totalQuestion;
		this.timesTaken = timesTaken;
		this.quizId = quizId;
	}

	public ResultRequest() {
		super();
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	public Long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Long getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Long correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Long getIncorrectAnswers() {
		return incorrectAnswers;
	}

	public void setIncorrectAnswers(Long incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}

	public Long getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Long totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public Long getTimesTaken() {
		return timesTaken;
	}

	public void setTimesTaken(Long timesTaken) {
		this.timesTaken = timesTaken;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}
	
}
