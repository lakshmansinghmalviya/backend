package com.example.quizapp.entity;
import java.time.LocalDateTime;

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
@Table(name = "quiz_attempted")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long score;
	
	@Column(nullable = false)
	private Long timeSpent;
	
	@Column(nullable = false)
	private Boolean isCompleted;
	
	@Column(nullable = false)
	private Boolean correctAnswers;
	
	@Column(nullable = false)
	private Boolean timesTaken; // for counting how many times user has attempted the quiz
	 
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
	private MyUser user;
	
    @ManyToOne()
    @JoinColumn(name = "quiz_id", nullable = false)
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
