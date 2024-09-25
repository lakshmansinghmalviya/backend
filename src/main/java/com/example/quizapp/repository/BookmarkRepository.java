package com.example.quizapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	boolean existsByUserIdAndQuizId(Long userId, Long id);

	Page<Bookmark> findByUserId(Long userId, Pageable pageable);

	Page<Bookmark> findByUserIdAndQuizTitleContainingIgnoreCaseOrQuizDescriptionContainingIgnoreCase(Long userId,
			String title, String description, Pageable pageable);
}
