package com.example.quizapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuizRepository;

@Service
public class QuizService {
	Logger logger = LoggerFactory.getLogger(QuizService.class);
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;

	public Quiz createQuiz(QuizRequest request) {
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
			return quiz;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}

	public Long getTotalQuiz(Long id) {
		try {
			return quizRepository.countByCreator_UserId(id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Quiz> getAllQuizByCreatorId(Long creatorId) {
		try {
			List<Quiz> quizzes = quizRepository.findByCreator_UserId(creatorId);
			return quizzes;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteQuizById(Long id) {
		try {
			if (quizRepository.existsById(id))
				quizRepository.deleteById(id);
			else
				throw new ResourceNotFoundException("Quiz id not found");

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Quiz updateQuizById(Long id, QuizRequest request) {
		try {
			Quiz quiz = quizRepository.findById(id).get();
			quiz.setTitle(request.getTitle());
			quiz.setDescription(request.getDescription());
			quiz.setRandomizeQuestions(request.getRandomizeQuestions());
			quiz.setTimeLimit(request.getTimeLimit());
			quiz.setQuizPic(request.getQuizPic());
			quiz = quizRepository.save(quiz);
			return quiz;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Quiz findById(Long id) {
		try {
			return quizRepository.findById(id).get();
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}

	public List<Quiz> getAllQuiz() {
		try {
			return quizRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}
}
