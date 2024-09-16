package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.User;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuizRepository;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	public MessageResponse createQuiz(QuizRequest request) {
		try {
			User creator = userService.getUserInfoUsingTokenInfo();
			Category category = categoryService.getCategoryById(request.getCategoryId());
			Quiz quiz = new Quiz();
			quiz.setTitle(request.getTitle());
			quiz.setDescription(request.getDescription());
			quiz.setRandomizeQuestions(request.getRandomizeQuestions());
			quiz.setTimeLimit(request.getTimeLimit());
			quiz.setQuizPic(request.getQuizPic());
			quiz.setCategory(category);
			quiz.setCreator(creator);
			quizRepository.save(quiz);
			return new MessageResponse("Quiz Created Successfully");
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}

	public List<Quiz> getAllQuizByCreatorId(Long creatorId) {
		try {
			return quizRepository.findByCreatorId(creatorId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Quiz> getAllByCategoryId(Long categoryId) {
		try {
			return quizRepository.findByCategoryId(categoryId);
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
			return quizRepository.save(quiz);
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

	public List<Quiz> getAllQuizOfCreator() {
		try {
			User user = userService.getUserInfoUsingTokenInfo();
			return quizRepository.findByCategoryId(user.getId());
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}

	public boolean exist(Long id) {
		try {
			return quizRepository.existsById(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Quiz not found " + e.getMessage());
		}
	}

	public List<Quiz> getTop4() {
		try {
			return quizRepository.getTop4(4);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Quizzes are found " + e.getMessage());
		}
	}

}
