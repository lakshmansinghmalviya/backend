package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.service.BookmarkService;
import com.example.quizapp.service.UserService;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookmarks")
@PreAuthorize("hasRole('Student')")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	@Autowired
	UserService userService;

	@Autowired
	CommonHelper commonHelper;

	@PostMapping()
	public ResponseEntity<UnifiedResponse<MessageResponse>> bookmark(@Valid @RequestBody BookmarkRequest request) {
		return ResponseBuilder.buildResponse(HttpStatus.CREATED, bookmarkService.bookmarkQuiz(request));
	}

	@GetMapping
	public ResponseEntity<UnifiedResponse<PageResponse<Bookmark>>> getAllBookmarksOfUser(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder
				.buildOKResponse(bookmarkService.getAllBookmarksOfUser(commonHelper.makePageReq(page, size)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UnifiedResponse<Void>> deleteBookmark(@PathVariable("id") Long id) {
		return ResponseBuilder.buildOKResponse(bookmarkService.deleteBookmarkById(id));
	}

	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Bookmark>>> searchBookmarks(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder
				.buildOKResponse(bookmarkService.searchBookmarksByQuery(query, commonHelper.makePageReq(page, size)));
	}
}
