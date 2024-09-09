package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.FeedbackRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.entity.Feedback;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.entity.Question;
import com.example.quizapp.repository.FeedbackRepository;

@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	UserService userService;

	@Autowired
	QuestionService questionService;

	public MessageResponse submitFeedback(FeedbackRequest request) {
		try {
			MyUser user = userService.getUser(request.getUserId());
			Question question = questionService.getQuestionById(request.getQuestionId());
			Feedback feedback  = feedbackRepository.findByUser_UserIdAndQuestion_Id(request.getUserId(), request.getQuestionId());
			if(feedback==null) {
				feedback = new Feedback();
			}
			feedback.setUser(user);
			feedback.setQuestion(question);
			feedback.setFeebackText(request.getFeebackText());				
			feedbackRepository.save(feedback);
			return new MessageResponse("Feedback submitted");
		} catch (Exception e) {
			throw new RuntimeException("Failed to save the feedback : " + e.getMessage());
		}
	}
}
