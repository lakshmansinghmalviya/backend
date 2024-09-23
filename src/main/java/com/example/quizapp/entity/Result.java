package com.example.quizapp.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@JsonManagedReference
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
}
