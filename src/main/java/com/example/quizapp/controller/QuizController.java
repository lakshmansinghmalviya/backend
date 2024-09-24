package com.example.quizapp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.service.QuizService;
import com.example.quizapp.util.CommonHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

	@Autowired
	QuizService quizService;

	@Autowired
	CommonHelper commonHelper;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Quiz>> createQuiz(@Valid @RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.createQuiz(request));
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getAllByCategoryId(
			@PathVariable("categoryId") Long categoryId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.getAllByCategoryId(categoryId, commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/creator/{creatorId}")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getAllByCreatorId(
			@PathVariable("creatorId") Long creatorId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.getAllByCreatorId(creatorId, commonHelper.makePageReq(page, size)));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Void>> deleteQuizById(@PathVariable("id") Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(quizService.deleteQuizById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Quiz>> updateQuizById(@PathVariable("id") Long id,
			@Valid @RequestBody QuizRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.updateQuizById(id, request));
	}

	@GetMapping("/public")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getAllQuiz(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuiz(commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/ofCreator")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getAllQuizOfCreator(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.getAllQuizOfCreator(commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/ofCreator/betweenDates/{start}/{end}")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> getQuizzesBetweenDates(@PathVariable String start,
			@PathVariable String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.getQuizzesByPaginationBetweenDates(start, end, commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> searchQuizzes(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.searchQuizzesByQuery(query, commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/student/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Quiz>>> searchQuizzesForStudent(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(quizService.searchQuizzesByQueryForStudent(query, commonHelper.makePageReq(page, size)));
	}
}
