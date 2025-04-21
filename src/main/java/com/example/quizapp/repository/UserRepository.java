package com.example.quizapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email = :email AND (u.isDeleted = false OR u.isDeleted IS NULL)")
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	@Query("SELECT CASE WHEN u.isApproved = true THEN true ELSE false END FROM User u WHERE u.email = :email AND (u.isDeleted = false OR u.isDeleted IS NULL)")
	boolean isApprovedByEmail(String email);

	Optional<User> findById(Long id);

	@Query("SELECT COUNT(u) FROM User u WHERE (u.isDeleted = false OR u.isDeleted IS NULL) AND u.role IN ('Educator', 'Student')")
	Long countTotalUsers();

	@Query("SELECT u FROM User u " + "WHERE u.role IN (:roles) " + " AND (u.isDeleted = false OR u.isDeleted IS NULL)"
			+ "AND (:startDate IS NULL OR u.createdAt >= :startDate) "
			+ "AND (:endDate IS NULL OR u.createdAt <= :endDate) "
			+ "AND (:query IS NULL OR (LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(u.education) LIKE LOWER(CONCAT('%', :query, '%')) "
			+ "OR LOWER(u.bio) LIKE LOWER(CONCAT('%', :query, '%'))))")
	Page<User> findUsersByFilters(List<Role> roles, LocalDateTime startDate, LocalDateTime endDate, String query,
			Pageable pageable);
}