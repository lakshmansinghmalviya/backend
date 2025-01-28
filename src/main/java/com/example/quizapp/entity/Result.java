package com.example.quizapp.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(nullable = false)
	private Long score;

	@Column(nullable = false)
	private Long totalScore;

	@Column(nullable = false)
	private Long totalQuestion;

	@Column(nullable = false)
	private Long timeSpent;

	@Column(nullable = false)
	private Boolean isCompleted;

	@Column(nullable = false)
	private Long correctAnswers;

	@Column(nullable = false)
	private Long incorrectAnswers;

	@Column(nullable = false)
	private Long timesTaken;

	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	@ManyToOne()
	@JoinColumn(name = "quiz_id", nullable = false)
	@JsonIgnoreProperties("results")
	private Quiz quiz;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public Result(Long id, Boolean isDeleted, Long score, Long totalScore, Long totalQuestion, Long timeSpent,
			Boolean isCompleted, Long correctAnswers, Long incorrectAnswers, Long timesTaken, User user, Quiz quiz,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.isDeleted = isDeleted;
		this.score = score;
		this.totalScore = totalScore;
		this.totalQuestion = totalQuestion;
		this.timeSpent = timeSpent;
		this.isCompleted = isCompleted;
		this.correctAnswers = correctAnswers;
		this.incorrectAnswers = incorrectAnswers;
		this.timesTaken = timesTaken;
		this.user = user;
		this.quiz = quiz;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public Result() {
		super();
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public Long getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Long totalQuestion) {
		this.totalQuestion = totalQuestion;
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

	public Long getTimesTaken() {
		return timesTaken;
	}

	public void setTimesTaken(Long timesTaken) {
		this.timesTaken = timesTaken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
