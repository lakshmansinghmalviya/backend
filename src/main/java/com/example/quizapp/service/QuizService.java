package com.example.quizapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.util.Codes;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	CommonHelper commonHelper;

	@Autowired
	UserHelper userHelper;

	public UnifiedResponse<Quiz> createQuiz(QuizRequest request) {
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
		return new UnifiedResponse(HttpStatus.OK.value(), "Quiz Created Successfully", null);
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllByCategoryId(Long categoryId, Pageable pageable) {
		return commonHelper.getPageResponse(quizRepository.findByCategoryId(categoryId, pageable));
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllByCreatorId(Long creatorId, Pageable pageable) {
		return commonHelper.getPageResponse(quizRepository.findByCreatorId(creatorId, pageable));
	}

	public UnifiedResponse<Void> deleteQuizById(Long id) {
		if (quizRepository.existsById(id)) {
			quizRepository.deleteById(id);
			return new UnifiedResponse(HttpStatus.OK.value(), "Quiz Deleted Successfully with id "+id, null);
		} else
			throwException(id);
		return new UnifiedResponse(HttpStatus.OK.value(), "Quiz Not Deleted", null);
	}

	public UnifiedResponse<Quiz> updateQuizById(Long id, QuizRequest request) {
		Quiz quiz = quizRepository.findById(id).orElseThrow(() -> throwException(id));
		quiz.setTitle(request.getTitle());
		quiz.setDescription(request.getDescription());
		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
		quiz.setTimeLimit(request.getTimeLimit());
		quiz.setQuizPic(request.getQuizPic());
		return new UnifiedResponse(Codes.OK,"udpated",quizRepository.save(quiz));
	}
	
	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElseThrow(() -> throwException(id));
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllQuiz(Pageable pageable) {
		return commonHelper.getPageResponse(quizRepository.findAll(pageable));
	}

	public UnifiedResponse<PageResponse<Quiz>> getAllQuizOfCreator(Pageable pageable) {
		User creator = userService.getUserInfoUsingTokenInfo();
		Page<Quiz> page = quizRepository.findByCreatorId(creator.getId(), pageable);
		return commonHelper.getPageResponse(page);
	}

	public UnifiedResponse<PageResponse<Quiz>> getQuizzesByPaginationBetweenDates(String start, String end,
			Pageable pageable) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime startDateTime = LocalDateTime.parse(start + " 00:00:00", formatter);
		LocalDateTime endDateTime = LocalDateTime.parse(end + " 23:59:59", formatter);
		Page<Quiz> quizzes = quizRepository.findQuizzesByCreatorIdAndBetweenDates(getUser().getId(), startDateTime,
				endDateTime, pageable);
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
