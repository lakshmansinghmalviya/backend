package com.example.quizapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.service.BookmarkService;

@RestController
@RequestMapping("/bookmarks")
@PreAuthorize("hasRole('Student')")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	@PostMapping()
	public ResponseEntity<MessageResponse> bookmark(@RequestBody BookmarkRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.bookmarkQuiz(request));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Quiz>> getAllBookmarksOfUser(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.getAllBookmarksOfUser(userId));
	}
}
