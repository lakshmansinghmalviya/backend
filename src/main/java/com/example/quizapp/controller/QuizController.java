package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

	@Autowired
	QuizService quizService;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<MessageResponse> createQuiz(@Valid @RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.createQuiz(request));
	}

	@GetMapping()
	public ResponseEntity<List<Quiz>> getAllQuiz() {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuiz());
	}

	@GetMapping("/ofCreator")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<List<Quiz>> getAllQuizOfCreator() {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuizOfCreator());
	}

	@GetMapping("/creator/{creatorId}")
	public ResponseEntity<List<Quiz>> getAllByCreatorId(@PathVariable("creatorId") Long creatorId) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuizByCreatorId(creatorId));
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Quiz>> getAllByCategoryId(@PathVariable("categoryId") Long categoryId) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllByCategoryId(categoryId));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Void> deleteQuizById(@PathVariable("id") Long id) {
		quizService.deleteQuizById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Quiz> updateQuizById(@PathVariable("id") Long id, @Valid @RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.updateQuizById(id, request));
	}
}
