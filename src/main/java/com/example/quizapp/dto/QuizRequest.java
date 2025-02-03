package com.example.quizapp.dto;

import com.example.quizapp.enums.Severity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
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

	public QuizRequest() {
	}

	public QuizRequest(String title, String description, String quizPic, Long timeLimit, Boolean randomizeQuestions,
			Long categoryId, Severity severity) {
		this.title = title;
		this.description = description;
		this.quizPic = quizPic;
		this.timeLimit = timeLimit;
		this.randomizeQuestions = randomizeQuestions;
		this.categoryId = categoryId;
		this.severity = severity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuizPic() {
		return quizPic;
	}

	public void setQuizPic(String quizPic) {
		this.quizPic = quizPic;
	}

	public Long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Boolean getRandomizeQuestions() {
		return randomizeQuestions;
	}

	public void setRandomizeQuestions(Boolean randomizeQuestions) {
		this.randomizeQuestions = randomizeQuestions;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	// Getters and setters
}
