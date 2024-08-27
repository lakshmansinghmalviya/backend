package com.example.quizapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByCreator_UserId(Long userId);
//	@Query("SELECT u FROM User u WHERE u.status = 1")
	Long countByCreator_UserId(Long userId);

}
