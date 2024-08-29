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
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.entity.Question;
import com.example.quizapp.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.create(request));
	}

	@PutMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> update(@RequestBody QuestionRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.update(request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Question> delete(@PathVariable("id") Long id) {
		questionService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/creator/{id}/total")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Long> getTotalQuestionOfTheEducator(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getTotalQuestionsOfTheEducator(id));
	}
}
