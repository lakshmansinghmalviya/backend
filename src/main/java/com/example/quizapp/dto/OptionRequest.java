package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionRequest {

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text should be between 1 and 300 characters.")
    private String text;

    @NotNull(message = "IsCorrect should not be null.")
    private Boolean isCorrect;

    private Long id;

    private String optionPic;
}

