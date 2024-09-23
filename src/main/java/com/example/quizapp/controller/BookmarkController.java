package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.service.BookmarkService;
import com.example.quizapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookmarks")
@PreAuthorize("hasRole('Student')")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	@Autowired
	UserService userService;

	@PostMapping()
	public ResponseEntity<UnifiedResponse<MessageResponse>> bookmark(@Valid @RequestBody BookmarkRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.bookmarkQuiz(request));
	}

	@GetMapping("/user")
	public ResponseEntity<UnifiedResponse<List<Quiz>>> getAllBookmarksOfUser() {
		return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.getAllBookmarksOfUser());
	}
}
