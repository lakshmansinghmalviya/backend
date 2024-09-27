package com.example.quizapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>,JpaSpecificationExecutor<Quiz> {

	Long countByCreatorId(Long id);

	boolean existsById(Long id);

	Page<Quiz> findByCreatorId(Long id, Pageable pageable);

	@Query("SELECT q FROM Quiz q WHERE q.creator.id = :creatorId AND q.createdAt BETWEEN :startDate AND :endDate")
	Page<Quiz> findQuizzesByCreatorIdAndBetweenDates(@Param("creatorId") Long creatorId,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

	Page<Quiz> findByCreatorIdAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Long creatorId,
			String title, String description, Pageable pageable);

	Page<Quiz> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description,
			Pageable pageable);

	Page<Quiz> findByCategoryId(Long categoryId, Pageable pageable);
}
