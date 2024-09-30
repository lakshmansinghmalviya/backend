package com.example.quizapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	boolean existsByUserIdAndQuizId(Long userId, Long id);

	Result findByUserIdAndQuizId(Long userId, Long id);

	@Query("SELECT r FROM Result r WHERE r.user.id = :id ORDER BY r.updatedAt DESC")
	Page<Result> findResultsByUserIdOrderedByUpdatedAtDesc(@Param("id") Long id, Pageable pageable);

	@Query("SELECT SUM(r.score) FROM Result r WHERE r.user.id = :id")
	Long findTotalScoreByUserId(@Param("id") Long id);

	@Query("SELECT SUM(r.totalScore) FROM Result r WHERE r.user.id = :id")
	Long findTotalOfTheTotalScoreByUserId(@Param("id") Long id);

	@Query("SELECT SUM(r.timeSpent) FROM Result r WHERE r.user.id = :id")
	Long findTotalTimeSpentByUserId(@Param("id") Long id);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :id AND r.isCompleted = true")
	Long findTotalCompletedQuizzesByUserId(@Param("id") Long id);

	@Query("SELECT COUNT(r) FROM Result r WHERE r.user.id = :id AND r.isCompleted = false")
	Long findTotalIncompleteQuizzesByUserId(@Param("id") Long id);

	@Query("SELECT r FROM Result r JOIN r.quiz q " + "WHERE r.user.id = :id AND ("
			+ "LOWER(q.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
			+ "OR STR(r.score) LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR STR(r.totalScore) LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR STR(r.timeSpent) LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR CASE WHEN r.isCompleted = true THEN 'Yes' ELSE 'No' END LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR STR(r.correctAnswers) LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR STR(r.incorrectAnswers) LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR STR(r.totalQuestion) LIKE CONCAT('%', :searchTerm, '%') "
			+ "OR STR(r.timesTaken) LIKE CONCAT('%', :searchTerm, '%'))")
	Page<Result> searchResults(@Param("id") Long id, @Param("searchTerm") String searchTerm, Pageable pageable);
}
