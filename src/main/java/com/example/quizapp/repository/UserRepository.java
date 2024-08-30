package com.example.quizapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quizapp.entity.*;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

	Optional<MyUser> findByUsername(String username);

	Boolean existsByUsername(String myUserRepo);

	MyUser findByUserId(Long userId);
}