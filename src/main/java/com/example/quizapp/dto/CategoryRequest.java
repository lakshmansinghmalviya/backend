package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

	@NotBlank(message = "Name should not be blank.")
    @Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.")
    private String name;

    @NotBlank(message = "Description should not be blank.")
    @Size(min = 10, max = 500, message = "Description should have between 10 and 500 characters.")
    private String description;

    private String categoryPic;
}
