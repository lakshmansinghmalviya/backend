package com.example.quizapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByUserId(Long id);

	Page<User> findByRole(String role, Pageable pageable);

	List<User> findByRole(String role);
}