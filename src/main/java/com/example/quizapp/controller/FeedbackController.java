package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.request.FeedbackRequest;
import com.example.quizapp.dto.response.MessageResponse;
import com.example.quizapp.dto.response.UnifiedResponse;
import com.example.quizapp.service.FeedbackService;
import com.example.quizapp.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/feedbacks")
public class FeedbackController {

	@Autowired
	FeedbackService feedbackService;

	@PostMapping()
	public ResponseEntity<UnifiedResponse<MessageResponse>> submitFeedback(
			@Valid @RequestBody FeedbackRequest request) {
		return ResponseBuilder.buildResponse(HttpStatus.CREATED, feedbackService.submitFeedback(request));
	}
}
