package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class EducatorProfileDataResponse {
    private Long totalQuiz;
    private Long totalQuestion;

    public EducatorProfileDataResponse() {
    }

    public EducatorProfileDataResponse(Long totalQuiz, Long totalQuestion) {
        this.totalQuiz = totalQuiz;
        this.totalQuestion = totalQuestion;
    }

    public Long getTotalQuiz() {
        return totalQuiz;
    }

    public void setTotalQuiz(Long totalQuiz) {
        this.totalQuiz = totalQuiz;
    }

    public Long getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Long totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
}
