package com.example.quizapp.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

    @NotNull(message = "Quiz ID should not be null.")
    private Long quizId;

    private Long id;

    @NotBlank(message = "Question type cannot be blank.")
    private String questionType;

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 3, max = 300, message = "Question text should be between 3 and 300 characters.")
    private String text;

    @NotNull(message = "Max score should not be null.")
    @Size(min = 1, message = "Max score must be at least 1.")
    private Long maxScore;

    private String questionPic;

    private Boolean randomizeOptions;

    @Size(min = 2, message = "At least two option must be provided.")
    private List<OptionRequest> options;
}
