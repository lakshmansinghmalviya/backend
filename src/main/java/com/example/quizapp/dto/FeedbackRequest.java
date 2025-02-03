package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class FeedbackRequest {

    @Size(min = 3, max = 100, message = "Feedback text should not exceed 100 characters and at least 3")
    private String feedbackText;

    @NotNull(message = "Question ID should not be null.")
    private Long questionId;

    public FeedbackRequest() {
    }

    public FeedbackRequest(String feedbackText, Long questionId) {
        this.feedbackText = feedbackText;
        this.questionId = questionId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}

