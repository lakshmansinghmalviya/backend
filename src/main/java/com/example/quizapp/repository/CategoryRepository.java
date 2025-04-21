package com.example.quizapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT COUNT(c) FROM Category c WHERE c.isDeleted = false")
	Long countTotalCategory();

	boolean existsByName(String name);

	@Query("SELECT c FROM Category c " + "WHERE (:creatorId IS NULL OR c.creator.id = :creatorId) "
			+ "AND (:startDate IS NULL OR c.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR c.createdAt <= :endDate) "
			+ "AND (:query IS NULL OR (LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')))) " + "AND c.isDeleted = false")
	Page<Category> findCategoriesByFilters(Long creatorId, LocalDateTime startDate, LocalDateTime endDate, String query,
			Pageable pageable);
}
