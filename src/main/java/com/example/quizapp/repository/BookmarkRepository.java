package com.example.quizapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	boolean existsByUserIdAndQuizId(Long userId, Long id);

	@Query("SELECT b FROM Bookmark b " + "WHERE (:userId IS NULL OR b.user.id = :userId) "
			+ "AND (:startDate IS NULL OR b.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR b.createdAt <= :endDate) "
			+ "AND (:query IS NULL OR (LOWER(b.quiz.title) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(b.quiz.description) LIKE LOWER(CONCAT('%', :query, '%')))) "
			+ "AND (:categoryId IS NULL OR b.quiz.category.id = :categoryId) "
			+ "AND (:timeLimit IS NULL OR b.quiz.timeLimit <= :timeLimit) "
			+ "AND (:randomizeQuestions IS NULL OR b.quiz.randomizeQuestions = :randomizeQuestions)")
	Page<Bookmark> findBookmarksByFilters(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate, @Param("query") String query, @Param("categoryId") Long categoryId,
			@Param("timeLimit") Long timeLimit, @Param("randomizeQuestions") Boolean randomizeQuestions,
			Pageable pageable);
}
