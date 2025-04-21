package com.example.quizapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Quiz;
import com.example.quizapp.enums.Severity;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	Long countByCreatorId(Long id);

	boolean existsById(Long id);

	boolean existsByTitle(String title);

	@Query("SELECT q FROM Quiz q " + "WHERE (:creatorId IS NULL OR q.creator.id = :creatorId) "
			+ "AND (q.isDeleted IS NULL OR q.isDeleted = false) " + "AND (:severity IS NULL OR q.severity = :severity) "
			+ "AND (:startDate IS NULL OR q.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR q.createdAt <= :endDate) "
			+ "AND (:query IS NULL OR (LOWER(q.title) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(q.description) LIKE LOWER(CONCAT('%', :query, '%')))) "
			+ "AND (:categoryId IS NULL OR q.category.id = :categoryId) "
			+ "AND (:timeLimit IS NULL OR q.timeLimit <= :timeLimit) "
			+ "AND (:randomizeQuestions IS NULL OR q.randomizeQuestions = :randomizeQuestions)")
	Page<Quiz> findQuizzesByFilters(Long creatorId, Severity severity, LocalDateTime startDate, LocalDateTime endDate,
			String query, Long categoryId, Long timeLimit, Boolean randomizeQuestions, Pageable pageable);
}
