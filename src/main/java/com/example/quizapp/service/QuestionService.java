package com.example.quizapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.OptionRequest;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.util.Codes;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizService quizService;

	@Autowired
	private OptionService optionService;

	@Autowired
	CommonHelper commonHelper;

	@Autowired
	UserHelper userHelper;

	public UnifiedResponse<PageResponse<Question>> getAllQuestionQuizId(Long id, Pageable pageable) {
		quizService.exist(id);
		return commonHelper.getPageResponse(questionRepository.findByQuizId(id, pageable));
	}

	@Transactional
	public UnifiedResponse<Question> create(QuestionRequest request) {
		Quiz quiz = quizService.findById(request.getQuizId());
		Question question = new Question();
		question.setText(request.getText());
		question.setIsDeleted(false);
		question.setQuestionPic(request.getQuestionPic());
		question.setQuiz(quiz);
		question.setCreator(getUser());
		question.setQuestionType(request.getQuestionType());
		question.setMaxScore(request.getMaxScore());
		question.setRandomizeOptions(request.getRandomizeOptions());
		question = questionRepository.save(question);
		for (OptionRequest optionReq : request.getOptions())
			optionService.createOption(optionReq, question);
		return commonHelper.returnUnifiedCREATED("Created", question);
	}

	public UnifiedResponse<Void> delete(Long id) {
		if (questionRepository.existsById(id))
			questionRepository.deleteById(id);
		else
			throwException(id);
		return commonHelper.returnUnifiedOK("Deleted", null);
	}

	@Transactional
	public UnifiedResponse<Question> update(Long id, QuestionRequest request) {

		Question question = getQuestionById(id);
		for (OptionRequest optionReq : request.getOptions())
			optionService.updateOption(optionReq);

		question.setText(request.getText());
		question.setQuestionPic(request.getQuestionPic());
		question.setQuestionType(request.getQuestionType());
		question.setMaxScore(request.getMaxScore());
		question.setRandomizeOptions(request.getRandomizeOptions());
		return commonHelper.returnUnifiedOK("Udpated", questionRepository.save(question));
	}

	public Question getQuestionById(Long id) {
		return questionRepository.findById(id).orElseThrow(() -> throwException(id));
	}

	public UnifiedResponse<PageResponse<Question>> getQuestionsByPagination(Pageable pageable) {
		Page<Question> questions = questionRepository.findByCreatorId(getUser().getId(), pageable);
		return commonHelper.getPageResponse(questions);
	}

	public UnifiedResponse<PageResponse<Question>> getQuestionsByPaginationBetweenDates(String startDate,
			String endDate, Pageable pageable) {
		LocalDateTime[] dates = commonHelper.parseDateRange(startDate, endDate);
		Page<Question> questions = questionRepository.findByCreatorIdAndDateBetween(getUser().getId(), dates[0],
				dates[1], pageable);
		return commonHelper.getPageResponse(questions);
	}

	public UnifiedResponse<PageResponse<Question>> searchQuestionsByQuery(String query, Pageable pageable) {
		Page<Question> questions = questionRepository
				.findByCreatorIdAndTextContainingIgnoreCaseOrQuestionTypeContainingIgnoreCase(getUser().getId(), query,
						query, pageable);
		return commonHelper.getPageResponse(questions);
	}

	public User getUser() {
		return userHelper.getUser();
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Question not found with the id " + id);
	}

}