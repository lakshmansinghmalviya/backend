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
import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Question;
import com.example.quizapp.service.QuestionService;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	CommonHelper commonHelper;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Question>> createQuestion(@Valid @RequestBody QuestionRequest request) {
		return ResponseBuilder.buildResponse(HttpStatus.CREATED, questionService.create(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Question>> update(@PathVariable("id") Long id,
			@Valid @RequestBody QuestionRequest request) {
		return ResponseBuilder.buildResponse(HttpStatus.OK, questionService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Void>> delete(@PathVariable("id") Long id) {
		return ResponseBuilder.buildOKResponse(questionService.delete(id));
	}

	@GetMapping("/filters")
	public ResponseEntity<UnifiedResponse<PageResponse<Question>>> findQuestionsByFilters(
			@RequestParam(required = false) String query, @RequestParam(required = false) Boolean randomizeOptions,
			@RequestParam(required = false) String questionType, @RequestParam(required = false) Long quizId,
			@RequestParam(required = false) Long creatorId, @RequestParam(required = false) String sort,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder.buildOKResponse(questionService.findQuestionsByFilters(creatorId, quizId, start, end,
				query, randomizeOptions, questionType, sort, commonHelper.makePageReq(page, size)));
	}
}
