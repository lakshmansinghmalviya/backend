package com.example.quizapp.dto;

import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionRequest {
	@NotBlank(message = "text can't be blank")
	private String text;

	@NotNull(message = "isCorrect should not be null")
	private Boolean isCorrect;
	
	private String optionPic;
}
