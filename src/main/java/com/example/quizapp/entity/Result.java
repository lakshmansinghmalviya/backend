package com.example.quizapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result extends BaseEntity {

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
	@JsonIgnoreProperties("results")
	private Quiz quiz;
}
