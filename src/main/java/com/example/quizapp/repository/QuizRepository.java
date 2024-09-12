package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	List<Quiz> findByCreator_UserId(Long userId);

	List<Quiz> findByCategory_Id(Long id);

	Long countByCreator_UserId(Long userId);

	boolean existsById(Long id);

	@Query(nativeQuery = true, value = "SELECT * FROM quizzes q  ORDER BY created_at DESC LIMIT :limit")
	List<Quiz> getTop4(@Param("limit") int limit);
}
