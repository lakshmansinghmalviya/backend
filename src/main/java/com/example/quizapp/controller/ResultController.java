package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.ResultRequest;
import com.example.quizapp.dto.StudentProfileResponse;
import com.example.quizapp.entity.Result;
import com.example.quizapp.service.ResultService;

@RestController
@RequestMapping("/results")
@PreAuthorize("hasRole('Student')")

public class ResultController {

	@Autowired
	ResultService resultService;

	@PostMapping()
	public ResponseEntity<MessageResponse> submitResult(@RequestBody ResultRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(resultService.attemptedQuiz(request));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Result>> getAllBookmarksOfUser(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(resultService.getAllAttemptedQuizOfUser(userId));
	}

	@GetMapping("/userProfileData/{userId}")
	public ResponseEntity<StudentProfileResponse> getUserProfileData(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(resultService.getUserProfileData(userId));
	}
}
