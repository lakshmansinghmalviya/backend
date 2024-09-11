package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quizapp.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	Long countByCreator_UserId(Long userId);

	List<Question> findByCreator_UserId(Long userId);

	List<Question> findAllByQuizId(Long id);
}
