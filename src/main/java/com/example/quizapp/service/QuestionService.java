package com.example.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.OptionRequest;
import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuestionRepository;

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
	private UserService userService;

	public List<Question> getAllByQuizId(Long id) {
		try {

			quizService.exist(id);
			return questionRepository.findAllByQuizId(id);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}

	@Transactional
	public Question create(QuestionRequest request) {
		try {
			Quiz quiz = quizService.findById(request.getQuizId());
			MyUser creator = userService.getUser(request.getCreatorId());
			Question question = new Question();
			question.setText(request.getText());
			question.setIsActive(true);
			question.setQuestionPic(request.getQuestionPic());
			question.setQuiz(quiz);
			question.setCreator(creator);
			question.setQuestionType(request.getQuestionType());
			question.setMaxScore(request.getMaxScore());
			question.setRandomizeOptions(request.getRandomizeOptions());
			question = questionRepository.save(question);
			for (OptionRequest optionReq : request.getOptions())
				optionService.createOption(optionReq, question);
			return question;
		} catch (Exception e) {
			throw new RuntimeException("Failed to create question: " + e.getMessage());
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			questionRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete question: " + e.getMessage());
		}
	}

	@Transactional
	public Question update(Long id, QuestionRequest request) {
		try {

			Question question = getQuestionById(request.getId());

			for (OptionRequest optionReq : request.getOptions())
				optionService.updateOption(optionReq);

			question.setText(request.getText());
			question.setIsActive(true);
			question.setQuestionPic(request.getQuestionPic());
			question.setQuestionType(request.getQuestionType());
			question.setMaxScore(request.getMaxScore());
			question.setRandomizeOptions(request.getRandomizeOptions());
			return questionRepository.save(question);
		} catch (Exception e) {
			throw new RuntimeException("Failed to update question: " + e.getMessage());
		}
	}

	public Long getTotalQuestionsOfTheEducator(Long id) {
		try {
			return questionRepository.countByCreator_UserId(id);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong " + e.getMessage());
		}
	}

	public List<Question> getAllQuestion() {
		try {
			return questionRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve questions : " + e.getMessage());
		}
	}

	public List<Question> getAllQuestionByCreatorId(Long id) {
		try {
			return questionRepository.findByCreator_UserId(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve questions : " + e.getMessage());
		}
	}

	public Question getQuestionById(Long id) {
		try {
			Optional<Question> question = questionRepository.findById(id);
			if (question.isPresent())
				return question.get();
			else
				throw new ResourceNotFoundException("Question not found");
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve question"+e.getMessage());
		}
	}

}