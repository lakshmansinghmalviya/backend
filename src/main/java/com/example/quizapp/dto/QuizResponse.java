package com.example.quizapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Severity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
	private Long id;
	private String title;
	private String description;
	private String quizPic;
	private Long timeLimit;
	private Boolean randomizeQuestions;
	private Severity severity;
	private Category category;
	private User creator;
	private List<Question> questions;
	private LocalDateTime createdAt;
	private Long attemptedUserCount;

	public QuizResponse() {
	}

	public QuizResponse(Long id, String title, String description, String quizPic, Long timeLimit,
			Boolean randomizeQuestions, Severity severity, Category category, User creator, List<Question> questions,
			LocalDateTime createdAt, Long attemptedUserCount) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.quizPic = quizPic;
		this.timeLimit = timeLimit;
		this.randomizeQuestions = randomizeQuestions;
		this.severity = severity;
		this.category = category;
		this.creator = creator;
		this.questions = questions;
		this.createdAt = createdAt;
		this.attemptedUserCount = attemptedUserCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getAttemptedUserCount() {
		return attemptedUserCount;
	}

	public void setAttemptedUserCount(Long attemptedUserCount) {
		this.attemptedUserCount = attemptedUserCount;
	}

	// Getters and setters
}
