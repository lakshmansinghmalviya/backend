package com.example.quizapp.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Severity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
	private Long id;
	private String title;
	private String description;
	private String quizPic;
	private Long timeLimit;
	private Boolean randomizeQuestions;
	private Severity severity;
	private Category category;
	private User creator;
	private List<Question> questions;
	private LocalDateTime createdAt;
	private Long attemptedUserCount;
}
