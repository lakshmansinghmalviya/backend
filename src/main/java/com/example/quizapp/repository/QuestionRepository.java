package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quizapp.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	Long countByCreatorId(Long id);

	List<Question> findByCreatorId(Long id);

	List<Question> findAllByQuizId(Long id);

	boolean existsById(Long id);
}
