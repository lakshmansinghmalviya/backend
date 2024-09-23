package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.FeedbackRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Feedback;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.User;
import com.example.quizapp.repository.FeedbackRepository;
import com.example.quizapp.util.Codes;

@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	UserService userService;

	@Autowired
	QuestionService questionService;

	public UnifiedResponse<MessageResponse> submitFeedback(FeedbackRequest request) {
		User user = userService.getUserInfoUsingTokenInfo();
		Question question = questionService.getQuestionById(request.getQuestionId());
		Feedback feedback = feedbackRepository.findByUserIdAndQuestionId(user.getId(), request.getQuestionId());
		if (feedback == null) {
			feedback = new Feedback();
		}
		feedback.setUser(user);
		feedback.setQuestion(question);
		feedback.setFeedbackText(request.getFeedbackText());
		feedbackRepository.save(feedback);
		return new UnifiedResponse(Codes.OK, "Sucess", new MessageResponse("Feedback submitted"));
	}
}
