package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.ResultRequest;
import com.example.quizapp.dto.StudentProfileResponse;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.Result;
import com.example.quizapp.repository.ResultRepository;

@Service
public class ResultService {

	@Autowired
	ResultRepository resultRepository;

	@Autowired
	UserService userService;

	@Autowired
	QuizService quizService;

	public MessageResponse attemptedQuiz(ResultRequest request) {

		try {

			Result quizAttemt = resultRepository.findByUser_UserIdAndQuiz_Id(request.getUserId(), request.getQuizId());
			if (quizAttemt != null)
				quizAttemt.setTimesTaken(quizAttemt.getTimesTaken() + 1);
			else
				quizAttemt = new Result();

			MyUser user = userService.getUser(request.getUserId());
			Quiz quiz = quizService.findById(request.getQuizId());
			quizAttemt.setQuiz(quiz);
			quizAttemt.setUser(user);
			quizAttemt.setScore(request.getScore());
			quizAttemt.setTotalScore(request.getTotalScore());
			quizAttemt.setTotalQuestion(request.getTotalQuestion());
			quizAttemt.setTimeSpent(request.getTimeSpent());
			quizAttemt.setIsCompleted(request.getIsCompleted());
			quizAttemt.setCorrectAnswers(request.getCorrectAnswers());
			quizAttemt.setIncorrectAnswers(request.getIncorrectAnswers());
			quizAttemt.setTimesTaken(quizAttemt.getTimesTaken() != null ? quizAttemt.getTimesTaken() : 1);
			resultRepository.save(quizAttemt);
			return new MessageResponse("Result is submitted successfully");
		} catch (Exception e) {
			throw new RuntimeException("Failed to save result please try again : " + e.getMessage());
		}
	}

	public List<Result> getAllAttemptedQuizOfUser(Long userId) {
		try {
			return resultRepository.findResultsByUserIdOrderedByUpdatedAtDesc(userId);
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch results  : " + e.getMessage());
		}
	}

	public StudentProfileResponse getUserProfileData(Long userId) {
		try {
			Long totalCompletedQuizzes = resultRepository.findTotalCompletedQuizzesByUserId(userId);
			Long totalInCompletedQuizzes = resultRepository.findTotalIncompleteQuizzesByUserId(userId);
			Long totalTimeSpent = resultRepository.findTotalTimeSpentByUserId(userId);
			Long totalOfTotalScore = resultRepository.findTotalOfTheTotalScoreByUserId(userId);
			Long totalScore = resultRepository.findTotalScoreByUserId(userId);
			return new StudentProfileResponse(totalCompletedQuizzes, totalInCompletedQuizzes, totalTimeSpent,
					totalOfTotalScore,totalScore);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get the user profile data : " + e.getMessage());
		}
	}

}
