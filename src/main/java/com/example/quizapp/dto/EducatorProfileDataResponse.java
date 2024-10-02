package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducatorProfileDataResponse {
	private Long totalCategory;
	private Long totalQuiz;
	private Long totalQuestion;
}