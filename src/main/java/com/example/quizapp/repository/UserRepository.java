package com.example.quizapp.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findById(Long id);

	Page<User> findByRole(Role role, Pageable pageable);

	@Query("SELECT u FROM User u " + "WHERE u.role = :role " + "AND (:startDate IS NULL OR u.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR u.createdAt <= :endDate) "
			+ "AND (:query IS NULL OR (LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(u.education) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(u.bio) LIKE LOWER(CONCAT('%', :query, '%'))))")
	Page<User> findUsersByFilters(@Param("role") Role role, @Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate, @Param("query") String query, Pageable pageable);
}