package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.ResultRequest;
import com.example.quizapp.dto.StudentProfileDataResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.Result;
import com.example.quizapp.service.ResultService;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/results")
@PreAuthorize("hasRole('Student')")
public class ResultController {

	@Autowired
	ResultService resultService;

	@Autowired
	CommonHelper commonHelper;
	
	@PostMapping()
	public ResponseEntity<UnifiedResponse<MessageResponse>> submitResult(@Valid @RequestBody ResultRequest request) {
		return ResponseBuilder.buildOKResponse(resultService.attemptedQuiz(request));
	}

	@GetMapping("/QuizResultsOfUser")
	public ResponseEntity<UnifiedResponse<PageResponse<Result>>> getAllQuizResultsOfUser(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder.buildOKResponse(resultService.getAllQuizResultsOfUser(commonHelper.makePageReq(page, size)));
	}
	
	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Result>>> searchResults(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder.buildOKResponse(resultService.searchResultsByQuery(query, commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/userProfileData")
	public ResponseEntity<UnifiedResponse<StudentProfileDataResponse>> getUserProfileData() {
		return ResponseBuilder.buildOKResponse(resultService.getUserProfileData());
	}
}
