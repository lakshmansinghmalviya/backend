package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.entity.QuizAttempt;

@Repository
public interface QuizAttemptedRepository extends JpaRepository<QuizAttempt, Long> {

	boolean existsByUser_UserIdAndQuiz_Id(Long userId, Long id);

	List<QuizAttempt> findAllByUser_UserId(Long userId);

	QuizAttempt findByUser_UserIdAndQuiz_Id(Long userId, Long id);
}
