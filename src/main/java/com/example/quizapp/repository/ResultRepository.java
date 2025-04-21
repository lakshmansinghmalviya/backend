package com.example.quizapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	boolean existsByUserIdAndQuizId(Long userId, Long id);

	Result findByUserIdAndQuizId(Long userId, Long id);

	Long countByQuizId(Long quizId);

	@Query("SELECT r FROM Result r WHERE r.user.id = :id ORDER BY r.updatedAt DESC")
	Page<Result> findResultsByUserIdOrderedByUpdatedAtDesc(Long id, Pageable pageable);

	@Query("SELECT SUM(r.score) FROM Result r WHERE r.user.id = :id")
	Long findTotalScoreByUserId(Long id);

	@Query("SELECT SUM(r.totalScore) FROM Result r WHERE r.user.id = :id")
	Long findTotalOfTheTotalScoreByUserId(Long id);

	@Query("SELECT SUM(r.timeSpent) FROM Result r WHERE r.user.id = :id")
	Long findTotalTimeSpentByUserId(Long id);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :id AND r.isCompleted = true")
	Long findTotalCompletedQuizzesByUserId(Long id);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :id AND r.isCompleted = false")
	Long findTotalIncompleteQuizzesByUserId(Long id);

	@Query("SELECT r FROM Result r JOIN r.quiz q " + "WHERE (:userId IS NULL OR r.user.id = :userId) "
			+ "AND (:query IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :query, '%'))) "
			+ "AND (:score IS NULL OR r.score = :score) " + "AND (:totalScore IS NULL OR r.totalScore = :totalScore) "
			+ "AND (:timeSpent IS NULL OR r.timeSpent = :timeSpent) "
			+ "AND (:isCompleted IS NULL OR r.isCompleted = :isCompleted) "
			+ "AND (:correctAnswers IS NULL OR r.correctAnswers = :correctAnswers) "
			+ "AND (:totalQuestion IS NULL OR r.totalQuestion = :totalQuestion) "
			+ "AND (:timesTaken IS NULL OR r.timesTaken = :timesTaken) "
			+ "AND (:startDate IS NULL OR r.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR r.createdAt <= :endDate) "
			+ "AND (:timeLimit IS NULL OR q.timeLimit <= :timeLimit)")
	Page<Result> searchResultsByFilters(Long userId, String query, Long score, Long totalScore, Long timeSpent,
			Boolean isCompleted, Long correctAnswers, Long totalQuestion, Long timesTaken, LocalDateTime startDate,
			LocalDateTime endDate, Long timeLimit, Pageable pageable);
}
