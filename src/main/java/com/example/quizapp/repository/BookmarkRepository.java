package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	
	boolean existsByUserIdAndQuizId(Long userId, Long id);
	
	List<Bookmark> findAllByUserId(Long userId);
}
