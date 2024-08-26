package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {
	private String title;
	private String description;
	private String quizPic;
	private Long timeLimit;
	private Boolean randomizeQuestions;
	private Long categoryId;
	private Long creatorId;
}
