package com.example.quizapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String feedbackText;

	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	@ManyToOne()
	@JoinColumn(name = "question_id", nullable = false)
	@JsonBackReference
	private Question question;
}
