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
import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Question;
import com.example.quizapp.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.create(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> update(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> delete(@PathVariable("id") Long id) {
		questionService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping()
	public ResponseEntity<List<Question>> getQuestions() {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getAllQuestion());
	}

	@GetMapping("/creator/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<List<Question>> getQuestionsByCreatorId(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getAllQuestionByCreatorId(id));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionById(id));
	}

	@GetMapping("/quiz/{id}")
	public ResponseEntity<UnifiedResponse<List<Question>>> getAllQuestionByQuizId(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getAllByQuizId(id));
	}

	@GetMapping("/ofCreator")
	public ResponseEntity<UnifiedResponse<PageResponse<Question>>> getQuestionsByPagination(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsByPagination(pageable));
	}

	@GetMapping("/ofCreator/betweenDates/{start}/{end}")
	public ResponseEntity<UnifiedResponse<PageResponse<Question>>> getQuestionsBetweenDates(@PathVariable String start,
			@PathVariable String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK)
				.body(questionService.getQuestionsByPaginationBetweenDates(start, end, pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Question>>> searchQuestions(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(questionService.searchQuestionsByQuery(query, pageable));
	}
}
