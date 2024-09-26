package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.ResultRequest;
import com.example.quizapp.dto.StudentProfileDataResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.entity.Result;
import com.example.quizapp.entity.User;
import com.example.quizapp.repository.ResultRepository;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class ResultService {

	@Autowired
	ResultRepository resultRepository;

	@Autowired
	QuizService quizService;

	@Autowired
	UserHelper userHelper;

	@Autowired
	CommonHelper commonHelper;

	public UnifiedResponse<MessageResponse> attemptedQuiz(ResultRequest request) {
		User user = getUser();
		Result quizAttemt = resultRepository.findByUserIdAndQuizId(user.getId(), request.getQuizId());
		if (quizAttemt != null)
			quizAttemt.setTimesTaken(quizAttemt.getTimesTaken() + 1);
		else
			quizAttemt = new Result();

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
		return commonHelper.returnUnifiedCREATED("Result is submitted successfully", null);
	}

	public UnifiedResponse<PageResponse<Result>> getAllQuizResultsOfUser(Pageable pageable) {
		return commonHelper.getPageResponse(resultRepository.findResultsByUserIdOrderedByUpdatedAtDesc(getUser().getId(),pageable));
	}

	public UnifiedResponse<StudentProfileDataResponse> getUserProfileData() {
		User user = getUser();
		Long totalCompletedQuizzes = resultRepository.findTotalCompletedQuizzesByUserId(user.getId());
		Long totalInCompletedQuizzes = resultRepository.findTotalIncompleteQuizzesByUserId(user.getId());
		Long totalTimeSpent = resultRepository.findTotalTimeSpentByUserId(user.getId());
		Long totalOfTotalScore = resultRepository.findTotalOfTheTotalScoreByUserId(user.getId());
		Long totalScore = resultRepository.findTotalScoreByUserId(user.getId());
		return commonHelper.returnUnifiedOK("Fetched", new StudentProfileDataResponse(totalCompletedQuizzes,
				totalInCompletedQuizzes, totalTimeSpent, totalOfTotalScore, totalScore));
	}

	public User getUser() {
		return userHelper.getUser();
	}
}
