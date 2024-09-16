package com.example.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

	List<Option> findAllByQuestionId(Long id);

	Integer deleteByQuestionId(Long id);
}
