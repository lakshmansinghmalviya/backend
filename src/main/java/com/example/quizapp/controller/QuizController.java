package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.UnifiedResponse;
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
	public ResponseEntity<UnifiedResponse<?>> createQuiz(@Valid @RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.createQuiz(request));
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

	@GetMapping("/public")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getAllQuiz(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuiz(pageable));
	}

	@GetMapping("/ofCreator")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getAllQuizOfCreator(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuizOfCreator(pageable));
	}

	@GetMapping("/ofCreator/betweenDates/{start}/{end}")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getQuizzesBetweenDates(@PathVariable String start,
			@PathVariable String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.getQuizzesByPaginationBetweenDates(start, end, pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> searchQuizzes(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(quizService.searchQuizzesByQuery(query, pageable));
	}
}
