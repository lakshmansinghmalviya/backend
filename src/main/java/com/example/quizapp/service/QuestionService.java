package com.example.quizapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.request.OptionRequest;
import com.example.quizapp.dto.request.QuestionRequest;
import com.example.quizapp.dto.response.PageResponse;
import com.example.quizapp.dto.response.UnifiedResponse;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceAlreadyExistsException;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuestionRepository;
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

	@Transactional
	public UnifiedResponse<Question> create(QuestionRequest request) {

		checkExistance(request.getText(), request.getQuizId());
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

		checkExistance(request.getText(), request.getQuizId());

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

	public UnifiedResponse<PageResponse<Question>> findQuestionsByFilters(Long creatorId, Long quizId, String startDate,
			String endDate, String query, Boolean randomizeOptions, String questionType, String sort,
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
		Page<Question> questions = questionRepository.findQuestionsByFilters(creatorId, quizId, dates[0], dates[1],
				query, randomizeOptions, questionType, pageable);
		return commonHelper.getPageResponse(questions);
	}

	public User getUser() {
		return userHelper.getUser();
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Question not found with the id " + id);
	}

	public ResourceAlreadyExistsException throwException(String message) {
		throw new ResourceNotFoundException(message);
	}

	public void checkExistance(String text, Long id) {
		if (questionRepository.existsByTextAndQuizId(text, id))
			throwException("Question already exits in the same quiz with same text");
	}
}