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
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	QuizService quizService;

	@Autowired
	CommonHelper commonHelper;

	@Autowired
	UserHelper userHelper;

	public UnifiedResponse<MessageResponse> bookmarkQuiz(BookmarkRequest request) {
		if (bookmarkRepository.existsByUserIdAndQuizId(getUser().getId(), request.getQuizId())) {
			return commonHelper.returnUnifiedCREATED("Already bookmarked please bookmark other one", null);
		}
		Quiz quiz = quizService.findById(request.getQuizId());
		Bookmark bookmark = new Bookmark();
		bookmark.setQuiz(quiz);
		bookmark.setUser(getUser());
		bookmarkRepository.save(bookmark);
		return commonHelper.returnUnifiedOK("Bookmarked", null);
	}

	public UnifiedResponse<PageResponse<Bookmark>> getAllBookmarksOfUser(Pageable pageable) {
		Page<Bookmark> bookmarks = bookmarkRepository.findByUserId(getUser().getId(), pageable);
		return commonHelper.getPageResponse(bookmarks);
	}

	public UnifiedResponse<Void> deleteBookmarkById(Long id) {
		if (!bookmarkRepository.existsById(id)) {
			throwException(id);
		}
		bookmarkRepository.deleteById(id);
		return commonHelper.returnUnifiedOK("Removed", null);
	}

	public UnifiedResponse<PageResponse<Bookmark>> searchBookmarksByQuery(String query, Pageable pageable) {
		Page<Bookmark> bookmarks = bookmarkRepository
				.findByUserIdAndQuizTitleContainingIgnoreCaseOrQuizDescriptionContainingIgnoreCase(getUser().getId(),
						query, query, pageable);
		return commonHelper.getPageResponse(bookmarks);
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Quiz not found with the id " + id);
	}

	public User getUser() {
		return userHelper.getUser();
	}

}
