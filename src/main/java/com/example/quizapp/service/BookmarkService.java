package com.example.quizapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.repository.BookmarkRepository;
import com.example.quizapp.util.Codes;

@Service
public class BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	UserService userService;

	@Autowired
	QuizService quizService;

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

	public UnifiedResponse<List<Quiz>> getAllBookmarksOfUser() {
		User user = userService.getUserInfoUsingTokenInfo();
		List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(user.getId());
		return new UnifiedResponse(Codes.OK, "success",
				bookmarks.stream().map(Bookmark::getQuiz).collect(Collectors.toList()));
	}
}
