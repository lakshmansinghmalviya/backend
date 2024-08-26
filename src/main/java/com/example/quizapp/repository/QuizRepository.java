package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	@Query("SELECT q FROM Quiz q WHERE q.creator.userId = :creatorId AND q.category.id = :categoryId")
	List<Quiz> findByCreatorIdAndCategoryId(@Param("creatorId") Long creatorId, @Param("categoryId") Long categoryId);
}
