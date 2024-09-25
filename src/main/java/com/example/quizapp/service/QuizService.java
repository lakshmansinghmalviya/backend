package com.example.quizapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	CommonHelper commonHelper;

	@Autowired
	UserHelper userHelper;

	public UnifiedResponse<Quiz> createQuiz(QuizRequest request) {
		Category category = categoryService.getCategoryById(request.getCategoryId());
		Quiz quiz = new Quiz();
		quiz.setTitle(request.getTitle());
		quiz.setDescription(request.getDescription());
		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
		quiz.setTimeLimit(request.getTimeLimit());
		quiz.setQuizPic(request.getQuizPic());
		quiz.setCategory(category);
		quiz.setCreator(getUser());
		quizRepository.save(quiz);
		return commonHelper.returnUnifiedCREATED("Quiz Created Successfully", null);
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllByCategoryId(Long categoryId, Pageable pageable) {
		return commonHelper.getPageResponse(quizRepository.findByCategoryId(categoryId, pageable));
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllByCreatorId(Long creatorId, Pageable pageable) {
		return commonHelper.getPageResponse(quizRepository.findByCreatorId(creatorId, pageable));
	}

	public UnifiedResponse<Void> deleteQuizById(Long id) {
		if (!quizRepository.existsById(id))
			throwException(id);
		quizRepository.deleteById(id);
		return commonHelper.returnUnifiedOK("Quiz Deleted Successfully with id " + id, null);
	}

	public UnifiedResponse<Quiz> updateQuizById(Long id, QuizRequest request) {
		Quiz quiz = quizRepository.findById(id).orElseThrow(() -> throwException(id));
		quiz.setTitle(request.getTitle());
		quiz.setDescription(request.getDescription());
		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
		quiz.setTimeLimit(request.getTimeLimit());
		quiz.setQuizPic(request.getQuizPic());
		return commonHelper.returnUnifiedOK("Udpated", quizRepository.save(quiz));
	}

	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElseThrow(() -> throwException(id));
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllQuiz(Pageable pageable) {
		return commonHelper.getPageResponse(quizRepository.findAll(pageable));
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllQuizOfCreator(Pageable pageable) {
		Page<Quiz> page = quizRepository.findByCreatorId(getUser().getId(), pageable);
		return commonHelper.getPageResponse(page);
	}

	public UnifiedResponse<PageResponse<Quiz>> getQuizzesByPaginationBetweenDates(String start, String end,
			Pageable pageable) {
		LocalDateTime[] dates = commonHelper.parseDateRange(start, end);
		Page<Quiz> quizzes = quizRepository.findQuizzesByCreatorIdAndBetweenDates(getUser().getId(), dates[0], dates[1],
				pageable);
		return commonHelper.getPageResponse(quizzes);
	}

	public UnifiedResponse<PageResponse<Quiz>> searchQuizzesByQuery(String query, Pageable pageable) {
		Page<Quiz> quizzes = quizRepository
				.findByCreatorIdAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(getUser().getId(), query,
						query, pageable);
		return commonHelper.getPageResponse(quizzes);
	}

	public UnifiedResponse<PageResponse<Quiz>> searchQuizzesByQueryForStudent(String query, Pageable pageable) {
		Page<Quiz> quizzes = quizRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query,
				query, pageable);
		return commonHelper.getPageResponse(quizzes);
	}

	public boolean exist(Long id) {
		return quizRepository.existsById(id);
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Quiz not found with the id " + id);
	}

	public User getUser() {
		return userHelper.getUser();
	}
}
