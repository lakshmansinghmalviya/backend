package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(nullable = false, unique = true)
	private String text;

	@Column(name = "question_type", nullable = false)
	private String questionType;

	@Column(name = "randomize_options")
	private Boolean randomizeOptions;

	@Column(name = "question_pic", columnDefinition = "TEXT")
	private String questionPic;

	@Column(name = "maxscore", nullable = false)
	private Long maxScore;

	@ManyToOne()
	@JoinColumn(name = "quiz_id", nullable = false)
	@JsonIgnoreProperties({ "categories", "quizzes", "results", "feedbacks", "bookmarks", "questions" })
	private Quiz quiz;

	@ManyToOne()
	@JoinColumn(name = "creator_id", nullable = false)
	@JsonBackReference
	private User creator;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Option> options;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
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
