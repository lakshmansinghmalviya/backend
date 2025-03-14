package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {
	@NotNull(message = "QuizId should not be null")
	private Long quizId;
}
