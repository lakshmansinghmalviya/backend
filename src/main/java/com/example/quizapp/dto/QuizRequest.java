package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {

    @NotNull(message = "Title cannot be null.")
    @Size(min = 3, max = 100, message = "Title should have between 3 and 100 characters.")
    private String title;

    @NotNull(message = "Description cannot be null.")
    @Size(min = 10, max = 300, message = "Description should have between 6 and 500 characters.")
    private String description;

    private String quizPic;

    @NotNull(message = "Time limit should not be null.")
    @Size(min = 1, message = "Time limit should be greater than 0.")
    private Long timeLimit;

    private Boolean randomizeQuestions;

    @NotNull(message = "Category ID should not be null.")
    private Long categoryId;
}
