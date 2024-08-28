package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.repository.QuestionRepository;

import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizService quizService;

	@Transactional
	public Question create(QuestionRequest request) {
		try {
			Quiz quiz = quizService.findById(request.getQuizId());
			Question question = new Question();
			question.setText(request.getText());
			question.setIsActive(true);
			question.setQuestionPic(request.getQuestionPic());
			question.setQuiz(quiz);
			question.setQuestionType(request.getQuestionType());
			question.setMaxScore(request.getMaxScore());
			question.setRandomizeOptions(request.getRandomizeOptions());
			question = questionRepository.save(question);
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

	public Question update(QuestionRequest request) {
		try {
			Question question = new Question();
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
}