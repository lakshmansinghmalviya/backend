package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.request.ResultRequest;
import com.example.quizapp.dto.response.MessageResponse;
import com.example.quizapp.dto.response.PageResponse;
import com.example.quizapp.dto.response.StudentProfileDataResponse;
import com.example.quizapp.dto.response.UnifiedResponse;
import com.example.quizapp.entity.Result;
import com.example.quizapp.service.ResultService;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/results")
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
	public ResponseEntity<UnifiedResponse<PageResponse<Result>>> getAllQuizResultsOfUser(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder
				.buildOKResponse(resultService.getAllQuizResultsOfUser(commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/userProfileData")
	public ResponseEntity<UnifiedResponse<StudentProfileDataResponse>> getUserProfileData() {
		return ResponseBuilder.buildOKResponse(resultService.getUserProfileData());
	}

	@GetMapping("/filters")
	public ResponseEntity<UnifiedResponse<PageResponse<Result>>> filterResults(
			@RequestParam(required = false) Long quizId, @RequestParam(required = false) String query,
			@RequestParam(required = false) Long totalScore, @RequestParam(required = false) Long timeSpent,
			@RequestParam(required = false) Long timeLimit, @RequestParam(required = false) Long correctAnswers,
			@RequestParam(required = false) Long totalQuestion, @RequestParam(required = false) Long timesTaken,
			@RequestParam(required = false) Long score, @RequestParam(required = false) Boolean isCompleted,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String start,
			@RequestParam(required = false) String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder.buildOKResponse(
				resultService.filterResults(quizId, query, score, totalScore, timeSpent, isCompleted, correctAnswers,
						totalQuestion, timesTaken, start, end, timeLimit, sort, commonHelper.makePageReq(page, size)));
	}
}
