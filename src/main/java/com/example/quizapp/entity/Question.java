package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Boolean isActive;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@Column(name = "question_type", nullable = false)
	private String questionType;

	@Column(name = "randomize_options")
	private Boolean randomizeOptions;

	@Column(name = "image_url", columnDefinition = "TEXT")
	private String imageUrl;

	@Column(name = "maxscore", nullable = false)
	private Long maxScore;

	@ManyToOne()
	@JoinColumn(name = "quiz_id", nullable = false)
	@JsonBackReference
	private Quiz quiz;

	@OneToMany(mappedBy = "question")
	@JsonManagedReference
	private List<Option> options;

	@OneToMany(mappedBy = "question")
	@JsonManagedReference
	private List<Feedback> feedbacks;

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
