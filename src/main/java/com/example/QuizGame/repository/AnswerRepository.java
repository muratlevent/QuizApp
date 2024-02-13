package com.example.QuizGame.repository;

import com.example.QuizGame.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA repository for Answer entities in the Quiz Game application.
 * This interface performing various operations on Answer entities.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    /**
     * Finds all answers associated with a specific question ID.
     *
     * @param questionId The ID of the question for which answers are to be found.
     * @return A list of Answer objects associated with the given question ID.
     */
    List<Answer> findByQuestionId(Long questionId);

}