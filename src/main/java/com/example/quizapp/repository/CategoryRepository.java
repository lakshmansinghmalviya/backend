package com.example.quizapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.quizapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.creator.userId = :creatorId")
    List<Category> findByCreatorId(@Param("creatorId") Long creatorId);
    
    @Query("SELECT c FROM Category c WHERE c.creator.userId = :creatorId AND c.id = :categoryId")
    Optional<Category> findByCreatorIdAndCategoryId(@Param("creatorId") Long creatorId,@Param("categoryId") Long categoryId);
}
