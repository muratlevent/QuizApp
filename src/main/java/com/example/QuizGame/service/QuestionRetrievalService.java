package com.example.QuizGame.service;

import com.example.QuizGame.model.Question;
import com.example.QuizGame.controller.SessionHelper;
import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * This class is responsible for retrieving questions from the database and managing the state of the questions
 * in the session.
 */
public class QuestionRetrievalService {
    private static final String VIEWING = "viewing";
    private static final String ANSWERED = "answered";
    private static final int QUESTION_COUNT = 10;

    private final RandomQuestionService randomQuestionService;
    private final SessionHelper sessionHelper;

    /**
     * Constructor for QuestionRetrievalService.
     *
     * @param session The HTTP session object, which is used to store and retrieve quiz state.
     * @param randomQuestionService The service used to retrieve random questions.
     */
    public QuestionRetrievalService(HttpSession session, RandomQuestionService randomQuestionService) {
        this.sessionHelper = new SessionHelper(session);
        this.randomQuestionService = randomQuestionService;
    }

    /**
     * Retrieves a list of random questions for the specified category. If the session is new or reset,
     * it initializes the session with a new set of questions. If the current state is 'answered',
     * it prepares the next question.
     *
     * @param category The category of the questions to retrieve.
     * @return A list of questions for the current quiz session.
     */
    public List<Question> retrieveQuestions(String category) {
        List<Question> randomQuestions = initializeOrRetrieveQuestions(category);
        prepareNextQuestionIfAnswered();
        return randomQuestions;
    }

    /**
     * Initializes or retrieves a list of questions from the session. If the session is new or reset,
     * it initializes the session with a new set of questions.
     *
     * @param category The category from which to retrieve questions.
     * @return A list of initialized or retrieved questions.
     */
    private List<Question> initializeOrRetrieveQuestions(String category) {
        List<Question> randomQuestions = sessionHelper.getAttribute("randomQuestions", List.class);
        Integer currentQuestionIndex = sessionHelper.getAttribute("currentQuestionIndex", Integer.class);
        String actionState = sessionHelper.getAttribute("actionState", String.class);

        if (randomQuestions == null || currentQuestionIndex == null || actionState == null) {
            randomQuestions = randomQuestionService.getRandomQuestionsByCategory(QUESTION_COUNT, category);
            sessionHelper.setAttribute("randomQuestions", randomQuestions);
            sessionHelper.setAttribute("currentQuestionIndex", 0);
            sessionHelper.setAttribute("actionState", VIEWING);
        }

        return randomQuestions;
    }

    /**
     * Prepares the next question if the previous one has been answered.
     * Updates the session state to 'viewing' for the next question.
     */
    private void prepareNextQuestionIfAnswered() {
        String actionState = sessionHelper.getAttribute("actionState", String.class);
        Integer currentQuestionIndex = sessionHelper.getAttribute("currentQuestionIndex", Integer.class);
        List<Question> randomQuestions = sessionHelper.getAttribute("randomQuestions", List.class);

        if (ANSWERED.equals(actionState) && currentQuestionIndex < randomQuestions.size() - 1) {
            sessionHelper.setAttribute("currentQuestionIndex", currentQuestionIndex + 1);
            sessionHelper.setAttribute("actionState", VIEWING);
        }
    }

}
