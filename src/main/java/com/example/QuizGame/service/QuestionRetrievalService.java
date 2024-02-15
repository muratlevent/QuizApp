package com.example.QuizGame.service;

import com.example.QuizGame.model.Question;
import com.example.QuizGame.controller.SessionHelper;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class QuestionRetrievalService {

    private final RandomQuestionService randomQuestionService;
    private final SessionHelper sessionHelper;

    public QuestionRetrievalService(HttpSession session, RandomQuestionService randomQuestionService) {
        this.sessionHelper = new SessionHelper(session);
        this.randomQuestionService = randomQuestionService;
    }

    public List<Question> retrieveQuestions(String category) {
        List<Question> randomQuestions = sessionHelper.getAttribute("randomQuestions", List.class);
        Integer currentQuestionIndex = sessionHelper.getAttribute("currentQuestionIndex", Integer.class);
        String actionState = sessionHelper.getAttribute("actionState", String.class);

        if (randomQuestions == null || currentQuestionIndex == null || actionState == null) {
            randomQuestions = randomQuestionService.getRandomQuestionsByCategory(10, category);
            sessionHelper.setAttribute("randomQuestions", randomQuestions);
            currentQuestionIndex = 0;
            sessionHelper.setAttribute("currentQuestionIndex", currentQuestionIndex);
            actionState = "viewing";
            sessionHelper.setAttribute("actionState", actionState);
        } else if ("answered".equals(actionState) && currentQuestionIndex < randomQuestions.size() - 1) {
            currentQuestionIndex++;
            sessionHelper.setAttribute("currentQuestionIndex", currentQuestionIndex);
            actionState = "viewing";
            sessionHelper.setAttribute("actionState", actionState);
        }

        return randomQuestions;
    }
}
