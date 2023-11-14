package com.example.QuizGame.service;

import com.example.QuizGame.model.*;
import com.example.QuizGame.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

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