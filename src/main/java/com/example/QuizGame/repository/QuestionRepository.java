package com.example.QuizGame.repository;

import com.example.QuizGame.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JPA repository for Question entities in the Quiz Game application.
 * This interface performing various operations on Question entities.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Finds all questions stored in the repository.
     *
     * @return A list of all Question objects.
     */
    List<Question> findAll();

    /**
     * Finds the first question in the repository ordered by ID in ascending order.
     *
     * @return The first Question object in the order of ID.
     */
    Question findFirstByOrderByIdAsc();

    /**
     * Finds a specific question by its ID, including its associated answers.
     *
     * @param questionId The ID of the question to find.
     * @return The Question object with its associated answers.
     */
    @Query("SELECT q FROM Question q JOIN FETCH q.answers WHERE q.id = :questionId")
    Question findQuestionWithAnswers(@Param("questionId") Long questionId);

    /**
     * Finds all questions in a specific category, including their associated answers.
     *
     * @param category The category to filter questions by.
     * @return A list of Question objects in the specified category with their associated answers.
     */
    @Query("SELECT q FROM Question q JOIN FETCH q.answers WHERE q.category = :category")
    List<Question> findQuestionsWithAnswersByCategory(@Param("category") String category);
}
