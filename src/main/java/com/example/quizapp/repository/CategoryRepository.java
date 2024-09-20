package com.example.quizapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByCreatorId(Long id);

	Long countByCreatorId(Long id);

	Page<Category> findByCreatorId(Long id, Pageable pageable);

	@Query("SELECT c FROM Category c WHERE c.creator.id = :creatorId AND c.createdAt BETWEEN :startDate AND :endDate")
	Page<Category> findCategoriesByCreatorIdAndBetweenDates(@Param("creatorId") Long creatorId,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

	Page<Category> findByCreatorIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Long creatorId,
			String name, String description, Pageable pageable);
}
