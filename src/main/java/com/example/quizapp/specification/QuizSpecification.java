package com.example.quizapp.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.quizapp.entity.Quiz;

import jakarta.persistence.criteria.Predicate;

public class QuizSpecification {

	public static Specification<Quiz> filterQuizzes(String title, String description, Long timeLimit,
			Boolean randomizeQuestions, Long categoryId, Long creatorId) {
		return (root, query, criteriaBuilder) -> {
			
			List<Predicate> predicates = new ArrayList<>();

			if (title != null && !title.isEmpty())
				predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));

			if (description != null && !description.isEmpty())
				predicates.add(criteriaBuilder.like(root.get("description"), "%" + description + "%"));

			if (timeLimit != null)
				predicates.add(criteriaBuilder.equal(root.get("timeLimit"), timeLimit));

			if (randomizeQuestions != null)
				predicates.add(criteriaBuilder.equal(root.get("randomizeQuestions"), randomizeQuestions));

			if (categoryId != null)
				predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));

			if (creatorId != null)
				predicates.add(criteriaBuilder.equal(root.get("creator").get("id"), creatorId));

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
