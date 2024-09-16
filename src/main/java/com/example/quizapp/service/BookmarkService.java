package com.example.quizapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.BookmarkRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.entity.User;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.exception.ResourceAlreadyExistsException;
import com.example.quizapp.repository.BookmarkRepository;

@Service
public class BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	UserService userService;

	@Autowired
	QuizService quizService;

	public MessageResponse bookmarkQuiz(BookmarkRequest request) {
		try {
            User user = userService.getUserInfoUsingTokenInfo();
			if (bookmarkRepository.existsByUserIdAndQuizId(user.getId(), request.getQuizId())) {
				return new MessageResponse("Already bookmarked please bookmark other one");
			}
			Quiz quiz = quizService.findById(request.getQuizId());
			Bookmark bookmark = new Bookmark();
			bookmark.setQuiz(quiz);
			bookmark.setUser(user);
			bookmarkRepository.save(bookmark);
			return new MessageResponse("bookmarked");
		} catch (Exception e) {
			throw new RuntimeException("Failed to bookmark quiz : " + e.getMessage());
		}
	}

	public List<Quiz> getAllBookmarksOfUser() {
		try {
			User user = userService.getUserInfoUsingTokenInfo();
			List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(user.getId());
			return bookmarks.stream().map(Bookmark::getQuiz).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch quiz : " + e.getMessage());
		}
	}
}
