package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.FeedbackRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Feedback;
import com.example.quizapp.entity.Question;
import com.example.quizapp.repository.FeedbackRepository;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	QuestionService questionService;

	@Autowired
	UserHelper userHelper;

	@Autowired
	CommonHelper commonHelper;

	public UnifiedResponse<MessageResponse> submitFeedback(FeedbackRequest request) {
		Question question = questionService.getQuestionById(request.getQuestionId());
		Feedback feedback = feedbackRepository.findByUserIdAndQuestionId(userHelper.getUser().getId(),
				request.getQuestionId());
		if (feedback == null) {
			feedback = new Feedback();
		}
		feedback.setUser(userHelper.getUser());
		feedback.setQuestion(question);
		feedback.setFeedbackText(request.getFeedbackText());
		feedbackRepository.save(feedback);
		return commonHelper.returnUnifiedCREATED("Submitted Successfully", null);
	}
}
