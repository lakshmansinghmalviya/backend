package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.BookmarkRepository;
import com.example.quizapp.util.Codes;
import com.example.quizapp.util.CommonHelper;

@Service
public class BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	UserService userService;

	@Autowired
	QuizService quizService;

	@Autowired
	CommonHelper commonHelper;

	public UnifiedResponse<MessageResponse> bookmarkQuiz(BookmarkRequest request) {
		User user = userService.getUserInfoUsingTokenInfo();
		if (bookmarkRepository.existsByUserIdAndQuizId(user.getId(), request.getQuizId())) {
			return new UnifiedResponse(Codes.OK, "Already bookmarked please bookmark other one", null);
		}
		Quiz quiz = quizService.findById(request.getQuizId());
		Bookmark bookmark = new Bookmark();
		bookmark.setQuiz(quiz);
		bookmark.setUser(user);
		bookmarkRepository.save(bookmark);
		return new UnifiedResponse(Codes.OK, "bookmarked", null);
	}

	public UnifiedResponse<PageResponse<Bookmark>> getAllBookmarksOfUser(Pageable pageable) {
		User user = userService.getUserInfoUsingTokenInfo();
		Page<Bookmark> bookmarks = bookmarkRepository.findByUserId(user.getId(), pageable);
		return commonHelper.getPageResponse(bookmarks);
	}

	public UnifiedResponse<Void> deleteBookmarkById(Long id) {
		if (!bookmarkRepository.existsById(id)) {
			throwException(id);
		}
		bookmarkRepository.deleteById(id);
		return new UnifiedResponse(Codes.OK, "Deleted", null);
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Category not found with the id " + id);
	}
}
