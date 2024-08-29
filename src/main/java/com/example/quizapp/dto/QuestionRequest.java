package com.example.quizapp.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

	@NotNull(message = "The quizId should not be null")
	private Long quizId;

	@NotNull(message = "The creatorId should not be null")
	private Long creatorId;

	@NotBlank(message = "questionType can't be blank")
	private String questionType;

	@NotBlank(message = "text can't be blank")
	private String text;

	@NotBlank(message = "maxScore can't be blank")
	private Long maxScore;

	private String questionPic;
	private Boolean randomizeOptions;
	private List<OptionRequest> options;
}
