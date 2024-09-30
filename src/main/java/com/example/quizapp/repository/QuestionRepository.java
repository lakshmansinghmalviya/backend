package com.example.quizapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	Long countByCreatorId(Long id);
	
	boolean existsById(Long id);

	@Query("SELECT q FROM Question q " + "WHERE (:creatorId IS NULL OR q.creator.id = :creatorId) "
			+ "AND (:quizId IS NULL OR q.quiz.id = :quizId) " + "AND (:startDate IS NULL OR q.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR q.createdAt <= :endDate) "
			+ "AND (:query IS NULL OR (LOWER(q.text) LIKE LOWER(CONCAT('%', :query, '%')))) "
			+ "AND (:randomizeOptions IS NULL OR q.randomizeOptions = :randomizeOptions) "
			+ "AND (:questionType IS NULL OR q.questionType = :questionType) " + "AND q.isDeleted = false")
	Page<Question> findQuestionsByFilters(@Param("creatorId") Long creatorId, @Param("quizId") Long quizId,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
			@Param("query") String query, @Param("randomizeOptions") Boolean randomizeOptions,
			@Param("questionType") String questionType, Pageable pageable);
}
