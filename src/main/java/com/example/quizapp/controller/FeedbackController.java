package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.FeedbackRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

	@Autowired
	FeedbackService feedbackService;

	@PostMapping()
	public ResponseEntity<MessageResponse> submitFeedback(@Valid @RequestBody FeedbackRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(feedbackService.submitFeedback(request));
	}
}
