package com.example.quizapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitedUsersRequest {

    @NotBlank(message = "Role should not be null or blank.")
    private String role;

    @Min(value = 0, message = "Page number should be zero or greater.")
    private int page;

    @Min(value = 1, message = "Size should be at least 1.")
    private int size;
}
