package com.example.quizapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.dto.LimitedUsersResponse;
import com.example.quizapp.entity.*;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

	Optional<MyUser> findByUsername(String username);

	boolean existsByUsername(String myUserRepo);

	MyUser findByUserId(Long userId);

	Page<MyUser> findByRole(String role, Pageable pageable);

	List<MyUser> findByRole(String role);
}