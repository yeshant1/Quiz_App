package com.lpu.question_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lpu.question_service.model.Question;

@Repository

public interface QuestionDao extends JpaRepository<Question, Integer> {

  List<Question> findByCategory(String category);

  @Query(value = "SELECT q.id FROM question q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
  List<Integer> findRandomQuestionsByCategory(@Param("category") String category, @Param("numQ") int numQ);

} 