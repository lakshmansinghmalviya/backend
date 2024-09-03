package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitedUsersResponse {
	private Long userId;
	private String name;
	private String profilePic;
	private String bio;
	private String education;
}

