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
import com.example.quizapp.dto.QuizResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.enums.Severity;
import com.example.quizapp.service.QuizService;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.ResponseBuilder;

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
		return ResponseBuilder.buildResponse(HttpStatus.CREATED, quizService.createQuiz(request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Void>> deleteQuizById(@PathVariable("id") Long id) {
		return ResponseBuilder.buildOKResponse(quizService.deleteQuizById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Quiz>> updateQuizById(@PathVariable("id") Long id,
			@Valid @RequestBody QuizRequest request) {
		return ResponseBuilder.buildOKResponse(quizService.updateQuizById(id, request));
	}

	@GetMapping("/filters")
	public ResponseEntity<UnifiedResponse<PageResponse<QuizResponse>>> filterQuizzes(
			@RequestParam(required = false) String query, @RequestParam(required = false) Severity severity,
			@RequestParam(required = false) Long timeLimit, @RequestParam(required = false) Boolean randomizeQuestions,
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long creatorId,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String start,
			@RequestParam(required = false) String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder.buildOKResponse(quizService.filterQuizzes(query, severity, start, end, timeLimit,
				randomizeQuestions, categoryId, creatorId, sort, commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/filters/public")
	public ResponseEntity<UnifiedResponse<PageResponse<QuizResponse>>> filterQuizzesForPublic(
			@RequestParam(required = false) String query, @RequestParam(required = false) Severity severity,
			@RequestParam(required = false) Long timeLimit, @RequestParam(required = false) Boolean randomizeQuestions,
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long creatorId,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String start,
			@RequestParam(required = false) String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder.buildOKResponse(quizService.filterQuizzes(query, severity, start, end, timeLimit,
				randomizeQuestions, categoryId, creatorId, sort, commonHelper.makePageReq(page, size)));
	}
}
