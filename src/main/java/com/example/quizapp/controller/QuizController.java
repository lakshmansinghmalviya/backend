package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.QuizResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.service.QuizService;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
	@Autowired
	QuizService quizService;

	@PostMapping()
	public ResponseEntity<QuizResponse> createQuiz(@RequestBody QuizRequest request) {
		return quizService.createQuiz(request);
	}

	@GetMapping()
	public ResponseEntity<List<Quiz>> getAllQuiz() {
		List<Quiz> quizzes = quizService.getAllQuiz();
		if (!quizzes.isEmpty())
			return ResponseEntity.ok(quizzes);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@GetMapping("/creator/{creatorId}/categories/{categoryId}")
	public ResponseEntity<List<Quiz>> getAllByCreatorAndCategoryId(@PathVariable("creatorId") Long creatorId,
			@PathVariable("categoryId") Long categoryId) {
		return quizService.getAllQuizByCreatorAndCategoryId(creatorId, categoryId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuizById(@PathVariable("id") Long id) {
		quizService.deleteQuizById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Quiz> updateQuizById(@PathVariable("id") Long id, @RequestBody QuizRequest request) {
		return quizService.updateQuizById(id, request);
	}
}
