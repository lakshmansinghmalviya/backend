package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.quizapp.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	boolean existsByUserIdAndQuizId(Long userId, Long id);

	Result findByUserIdAndQuizId(Long userId, Long id);

	@Query("SELECT r FROM Result r WHERE r.user.id = :id ORDER BY r.updatedAt DESC")
	List<Result> findResultsByUserIdOrderedByUpdatedAtDesc(@Param("id") Long id);

	@Query("SELECT SUM(r.score) FROM Result r WHERE r.user.id = :id")
	Long findTotalScoreByUserId(@Param("id") Long id);

	@Query("SELECT SUM(r.totalScore) FROM Result r WHERE r.user.id = :id")
	Long findTotalOfTheTotalScoreByUserId(@Param("id") Long id);

	@Query("SELECT SUM(r.timeSpent) FROM Result r WHERE r.user.id = :id")
	Long findTotalTimeSpentByUserId(@Param("id") Long id);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :id AND r.isCompleted = true")
	Long findTotalCompletedQuizzesByUserId(@Param("id") Long id);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :id AND r.isCompleted = false")
	Long findTotalIncompleteQuizzesByUserId(@Param("id") Long id);
}
