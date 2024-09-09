package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Bookmark;
import com.example.quizapp.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	boolean existsByUser_UserIdAndQuiz_Id(Long userId, Long id); 
	Result findByUser_UserIdAndQuiz_Id(Long userId, Long id);

	@Query("SELECT r FROM Result r WHERE r.user.userId = :userId ORDER BY r.updatedAt DESC")
	List<Result> findResultsByUserIdOrderedByUpdatedAtDesc(@Param("userId") Long userId);
}
