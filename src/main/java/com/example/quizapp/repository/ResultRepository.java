package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.quizapp.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	boolean existsByUser_UserIdAndQuiz_Id(Long userId, Long id);

	Result findByUser_UserIdAndQuiz_Id(Long userId, Long id);

	@Query("SELECT r FROM Result r WHERE r.user.userId = :userId ORDER BY r.updatedAt DESC")
	List<Result> findResultsByUserIdOrderedByUpdatedAtDesc(@Param("userId") Long userId);

	@Query("SELECT SUM(r.score) FROM Result r WHERE r.user.userId = :userId")
	Long findTotalScoreByUserId(@Param("userId") Long userId);

	@Query("SELECT SUM(r.totalScore) FROM Result r WHERE r.user.userId = :userId")
	Long findTotalOfTheTotalScoreByUserId(@Param("userId") Long userId);

	@Query("SELECT SUM(r.timeSpent) FROM Result r WHERE r.user.userId = :userId")
	Long findTotalTimeSpentByUserId(@Param("userId") Long userId);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :userId AND r.isCompleted = true")
	Long findTotalCompletedQuizzesByUserId(@Param("userId") Long userId);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :userId AND r.isCompleted = false")
	Long findTotalIncompleteQuizzesByUserId(@Param("userId") Long userId);
}
