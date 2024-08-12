package com.example.quizapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.quizapp.entity.*;

public interface UserRepository extends JpaRepository<MyUser, Long> {
	Optional<MyUser> findByUsername(String username);

	Boolean existsByUsername(String myUserRepo);
}
