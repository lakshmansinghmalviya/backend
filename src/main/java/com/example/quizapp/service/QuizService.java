package com.example.quizapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.QuizResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.UserRepository;

@Service
public class QuizService {
	Logger logger = LoggerFactory.getLogger(QuizService.class);
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;

	@Transactional
	public QuizResponse createQuiz(QuizRequest request) {
		try {
			MyUser creator = userService.getUser(request.getCreatorId());
			Category category = categoryService.getCategoryById(request.getCategoryId());
			Quiz quiz = new Quiz();
			quiz.setTitle(request.getTitle());
			quiz.setDescription(request.getDescription());
			quiz.setRandomizeQuestions(request.getRandomizeQuestions());
			quiz.setTimeLimit(request.getTimeLimit());
			quiz.setQuizPic(request.getQuizPic());
			quiz.setCategory(category);
			quiz.setCreator(creator);
			quiz = quizRepository.save(quiz);
			return new QuizResponse("Quiz Created!");
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

//	public ResponseEntity<List<Quiz>> getAllQuizByCreatorAndCategoryId(Long creatorId, Long categoryId) {
//		try {
//			List<Quiz> quizzes = quizRepository.findByCreatorIdAndCategoryId(creatorId, categoryId);
//			if (quizzes.isEmpty()) {
//				return ResponseEntity.noContent().build();
//			}
//			return ResponseEntity.ok(quizzes);
//		} catch (Exception e) {
//			return ResponseEntity.internalServerError().build();
//		}
//	}

//	@Transactional
//	public void deleteQuizById(Long id) {
//		quizRepository.deleteById(id);
//	}
//
//	@Transactional
//	public ResponseEntity<Quiz> updateQuizById(Long id, QuizRequest request) {
//		Quiz quiz = quizRepository.findById(id).get();
//		quiz.setId(id);
//		quiz.setTitle(request.getTitle());
//		quiz.setDescription(request.getDescription());
//		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
//		quiz.setTimeLimit(request.getTimeLimit());
//		quiz = quizRepository.save(quiz);
//		if (quiz != null) {
//			return ResponseEntity.ok(quiz);
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	}
//
//	public Quiz findById(Long id) {
//		return quizRepository.findById(id).get();
//	}
//
//	public List<Quiz> getAllQuiz() {
//		return quizRepository.findAll();
//	}
}
