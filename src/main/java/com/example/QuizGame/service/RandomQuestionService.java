package com.example.QuizGame.service;

import com.example.QuizGame.model.*;
import com.example.QuizGame.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for retrieving random questions from the Quiz Game application's question repository.
 * This service is responsible for fetching a specified number of random questions,
 * either from the entire question pool or from a specific category.
 */
@Service
public class RandomQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Retrieves a specified number of random questions from all available questions.
     *
     * @param count The number of random questions to retrieve.
     * @return A list of Question objects selected randomly.
     */
    public List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = questionRepository.findAll();
        List<Question> randomQuestions = new ArrayList<>();

        while (randomQuestions.size() < count && !allQuestions.isEmpty()) {
            int randomIndex = new Random().nextInt(allQuestions.size());
            Question randomQuestion = allQuestions.get(randomIndex);
            Question questionWithAnswers = questionRepository.findQuestionWithAnswers(randomQuestion.getId());

            if (questionWithAnswers != null) {
                randomQuestions.add(questionWithAnswers);
            }

            allQuestions.remove(randomIndex);
        }

        return randomQuestions;
    }

    /**
     * Retrieves a specified number of random questions from a specific category.
     *
     * @param count The number of random questions to retrieve.
     * @param category The category from which to retrieve the questions.
     * @return A list of Question objects from the specified category, selected randomly.
     */
    public List<Question> getRandomQuestionsByCategory(int count, String category) {
        List<Question> allQuestionsByCategory = questionRepository.findQuestionsWithAnswersByCategory(category);
        List<Question> randomQuestions = new ArrayList<>();

        while (randomQuestions.size() < count && !allQuestionsByCategory.isEmpty()) {
            int randomIndex = new Random().nextInt(allQuestionsByCategory.size());
            Question randomQuestion = allQuestionsByCategory.get(randomIndex);
            Question questionWithAnswers = questionRepository.findQuestionWithAnswers(randomQuestion.getId());

            if (questionWithAnswers != null) {
                randomQuestions.add(questionWithAnswers);
            }

            allQuestionsByCategory.remove(randomIndex);
        }

        return randomQuestions;
    }

}