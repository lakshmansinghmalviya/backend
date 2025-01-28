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
	
	public StudentProfileDataResponse(Long totalCompletedQuizzes, Long totalInCompletedQuizzes, Long totalTimeSpent,
			Long totalOfTotalScore, Long totalScore) {
		super();
		this.totalCompletedQuizzes = totalCompletedQuizzes;
		this.totalInCompletedQuizzes = totalInCompletedQuizzes;
		this.totalTimeSpent = totalTimeSpent;
		this.totalOfTotalScore = totalOfTotalScore;
		this.totalScore = totalScore;
	}
	
	public StudentProfileDataResponse() {
		super();
	}

	public Long getTotalCompletedQuizzes() {
		return totalCompletedQuizzes;
	}

	public void setTotalCompletedQuizzes(Long totalCompletedQuizzes) {
		this.totalCompletedQuizzes = totalCompletedQuizzes;
	}

	public Long getTotalInCompletedQuizzes() {
		return totalInCompletedQuizzes;
	}

	public void setTotalInCompletedQuizzes(Long totalInCompletedQuizzes) {
		this.totalInCompletedQuizzes = totalInCompletedQuizzes;
	}

	public Long getTotalTimeSpent() {
		return totalTimeSpent;
	}

	public void setTotalTimeSpent(Long totalTimeSpent) {
		this.totalTimeSpent = totalTimeSpent;
	}

	public Long getTotalOfTotalScore() {
		return totalOfTotalScore;
	}

	public void setTotalOfTotalScore(Long totalOfTotalScore) {
		this.totalOfTotalScore = totalOfTotalScore;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}
}
