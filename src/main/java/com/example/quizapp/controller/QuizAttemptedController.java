package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.ResultRequest;
import com.example.quizapp.service.QuizAttemptedService;

@RestController
@RequestMapping("/quizAttempted")
public class QuizAttemptedController {

	@Autowired
	QuizAttemptedService quizAttemptedService;

	@PostMapping()
	public ResponseEntity<MessageResponse> submitResult(@RequestBody ResultRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(quizAttemptedService.attemptedQuiz(request));
	}

//	@GetMapping("/user/{userId}")
//	public ResponseEntity<List<Quiz>> getAllBookmarksOfUser(@PathVariable Long userId) {
//		return ResponseEntity.status(HttpStatus.OK).body(quizAttemptedService.getAllBookmarksOfUser(userId));
//	}
}
