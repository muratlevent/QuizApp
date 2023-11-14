package com.example.QuizGame.repository;

import com.example.QuizGame.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll();

    Question findFirstByOrderByIdAsc();

    @Query("SELECT q FROM Question q JOIN FETCH q.answers WHERE q.id = :questionId")
    Question findQuestionWithAnswers(@Param("questionId") Long questionId);

    @Query("SELECT q FROM Question q JOIN FETCH q.answers WHERE q.category = :category")
    List<Question> findQuestionsWithAnswersByCategory(@Param("category") String category);
}
