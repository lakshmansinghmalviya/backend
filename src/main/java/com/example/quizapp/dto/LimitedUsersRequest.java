package com.example.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitedUsersRequest {

	@NotNull(message = "role should not be null or blank")
	private String role;

	@Size(min = 0, message = "Minimum page should be zero ")
	private int page;

	@Size(min = 1, message = "Sie should be zero ")
	private int size;
}
