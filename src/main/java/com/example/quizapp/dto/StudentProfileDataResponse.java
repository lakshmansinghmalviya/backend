package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfileDataResponse {
	private Long totalCompletedQuizzes;
	private Long totalInCompletedQuizzes;
	private Long totalTimeSpent;
	private Long totalOfTotalScore;
	private Long totalScore;
}
