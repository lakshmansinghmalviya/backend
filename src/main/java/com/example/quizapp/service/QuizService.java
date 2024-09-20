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
	}

	public List<Quiz> getAllQuizByCreatorId(Long creatorId) {
		return quizRepository.findByCreatorId(creatorId);
	}

	public List<Quiz> getAllByCategoryId(Long categoryId) {
		return quizRepository.findByCategoryId(categoryId);
	}

	public void deleteQuizById(Long id) {
		if (quizRepository.existsById(id))
			quizRepository.deleteById(id);
		else
			throwException(id);
	}

	public Quiz updateQuizById(Long id, QuizRequest request) {
		Quiz quiz = quizRepository.findById(id).orElseThrow(() -> throwException(id));
		quiz.setTitle(request.getTitle());
		quiz.setDescription(request.getDescription());
		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
		quiz.setTimeLimit(request.getTimeLimit());
		quiz.setQuizPic(request.getQuizPic());
		return quizRepository.save(quiz);
	}

	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElseThrow(() -> throwException(id));
	}

	public List<Quiz> getAllQuiz() {
		return quizRepository.findAll();
	}

	public List<Quiz> getAllQuizOfCreator() {
		User user = userService.getUserInfoUsingTokenInfo();
		return quizRepository.findByCategoryId(user.getId());
	}

	public boolean exist(Long id) {
		return quizRepository.existsById(id);
	}

	public List<Quiz> getTop4() {
		return quizRepository.getTop4(4);
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Quiz not found with the id " + id);
	}
}
