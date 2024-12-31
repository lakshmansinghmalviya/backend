package com.example.quizapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Severity;
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

	public UnifiedResponse<Void> deleteBookmarkById(Long id) {
		if (!bookmarkRepository.existsById(id)) {
			throwException(id);
		}
		bookmarkRepository.deleteById(id);
		return commonHelper.returnUnifiedOK("Removed", null);
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Quiz not found with the id " + id);
	}

	public User getUser() {
		return userHelper.getUser();
	}

	public UnifiedResponse<PageResponse<Bookmark>> findBookmarksByFilters(String startDate, String endDate,
			String query, Severity severity, Long categoryId, Long timeLimit, Boolean randomizeQuestions, String sort,
			Pageable pageable) {
		Long userId = null;
		if (getUser().getRole().toString().equals("Student"))
			userId = getUser().getId();

		LocalDateTime[] dates = { null, null };

		if (startDate != null && endDate != null) {
			dates = commonHelper.parseDateRange(startDate, endDate);
		}

		if (sort != null) {
			Sort sorting = commonHelper.parseSortString(sort);
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
		}

		Page<Bookmark> bookmarks = bookmarkRepository.findBookmarksByFilters(userId, dates[0], dates[1], query,
				severity, categoryId, timeLimit, randomizeQuestions, pageable);
		return commonHelper.getPageResponse(bookmarks);
	}
}