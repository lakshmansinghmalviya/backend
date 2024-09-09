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
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.service.QuizService;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
	@Autowired
	QuizService quizService;

	@PostMapping()
	public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.createQuiz(request));
	}
     //for student 
	@GetMapping()
	public ResponseEntity<List<Quiz>> getAllQuiz() {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuiz());
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
	public ResponseEntity<?> deleteQuizById(@PathVariable("id") Long id) {
		quizService.deleteQuizById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Quiz> updateQuizById(@PathVariable("id") Long id, @RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.updateQuizById(id, request));
	}

	@GetMapping("/creator/{id}/{total}")
	public ResponseEntity<Long> getTotalNumberOfQuizzes(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getTotalQuiz(id));
	}
}
