package com.example.quizapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.QuizRequest;
import com.example.quizapp.dto.QuizResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Severity;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.ResultRepository;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private ResultRepository resultRepository;

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
		quiz.setIsDeleted(false);
		quiz.setSeverity(request.getSeverity());
		quiz.setDescription(request.getDescription());
		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
		quiz.setTimeLimit(request.getTimeLimit());
		quiz.setQuizPic(request.getQuizPic());
		quiz.setCategory(category);
		quiz.setCreator(getUser());
		quizRepository.save(quiz);
		return commonHelper.returnUnifiedCREATED("Quiz Created Successfully", null);
	}

	public UnifiedResponse<Void> deleteQuizById(Long id) {
		if (!quizRepository.existsById(id))
			throwException(id);
		Quiz quiz = findById(id);
		quiz.setIsDeleted(true);
		return commonHelper.returnUnifiedOK("Quiz Deleted Successfully with id " + id, null);
	}

	public UnifiedResponse<Quiz> updateQuizById(Long id, QuizRequest request) {
		Quiz quiz = quizRepository.findById(id).orElseThrow(() -> throwException(id));
		quiz.setTitle(request.getTitle());
		quiz.setSeverity(request.getSeverity());
		quiz.setDescription(request.getDescription());
		quiz.setRandomizeQuestions(request.getRandomizeQuestions());
		quiz.setTimeLimit(request.getTimeLimit());
		quiz.setQuizPic(request.getQuizPic());
		return commonHelper.returnUnifiedOK("Udpated", quizRepository.save(quiz));
	}

	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElseThrow(() -> throwException(id));
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

	public UnifiedResponse<PageResponse<QuizResponse>> filterQuizzes(String query, Severity severity, String startDate,
			String endDate, Long timeLimit, Boolean randomizeQuestions, Long categoryId, Long creatorId, String sort,
			Pageable pageable) {

		if (getUser().getRole().toString().equals("Educator"))
			creatorId = getUser().getId();

		LocalDateTime[] dates = { null, null };

		if (startDate != null && endDate != null) {
			dates = commonHelper.parseDateRange(startDate, endDate);
		}

		if (sort != null) {
			Sort sorting = commonHelper.parseSortString(sort);
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
		}

		Page<Quiz> quizzes = quizRepository.findQuizzesByFilters(creatorId, severity, dates[0], dates[1], query,
				categoryId, timeLimit, randomizeQuestions, pageable);

		List<QuizResponse> quizResponses = quizzes.stream().map(quiz -> {
			QuizResponse response = mapToQuizResponse(quiz);
			Long attemptedUserCount = resultRepository.countByQuizId(quiz.getId());
			response.setAttemptedUserCount(attemptedUserCount);
			return response;
		}).collect(Collectors.toList());

		return commonHelper.getPageResponse(quizzes, quizResponses);
	}

	private QuizResponse mapToQuizResponse(Quiz quiz) {
		return new QuizResponse(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getQuizPic(),
				quiz.getTimeLimit(), quiz.getRandomizeQuestions(), quiz.getSeverity(), quiz.getCategory(),
				quiz.getCreator(), quiz.getQuestions(), quiz.getCreatedAt(), 0l);
	}
}