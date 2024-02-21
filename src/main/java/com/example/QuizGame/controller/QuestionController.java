package com.example.QuizGame.controller;

import com.example.QuizGame.model.Answer;
import com.example.QuizGame.model.Question;
import com.example.QuizGame.repository.*;
import com.example.QuizGame.service.QuestionRetrievalService;
import com.example.QuizGame.service.RandomQuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller for handling quiz game related requests.
 * This controller manages the quiz game flow, including starting a quiz, selecting categories,
 * fetching and answering questions, and handling the completion of the quiz.
 */
@Controller
@RequestMapping(path = "/")
public class QuestionController {

    @Autowired
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final RandomQuestionService randomQuestionService;

    /**
     * Constructor for QuestionController.
     *
     * @param questionRepository Repository for accessing question data.
     * @param answerRepository Repository for accessing answer data.
     * @param randomQuestionService Service for fetching random questions.
     */
    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository,
                              RandomQuestionService randomQuestionService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.randomQuestionService = randomQuestionService;
    }

    @Autowired
    private HttpSession httpSession;

    /**
     * Starts the quiz and initializes the session using SessionHelper.
     *
     * @param httpSession Session object to store quiz data.
     * @param model Model object to add attributes used for rendering views.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/start")
    public String startQuiz(HttpSession httpSession, Model model) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        sessionHelper.initializeQuiz();
        addUserDetailsToModel(httpSession, model);

        return "start";
    }

    /**
     * Shows the categories page for the quiz.
     *
     * @return The name of the view to be rendered.
     */
    @GetMapping("/categories")
    public String showCategories() {
        return "categories";
    }

    /**
     * Selects a category for the quiz and stores it in the session using SessionHelper.
     *
     * @param category The selected quiz category.
     * @param httpSession The session object that will hold the quiz state.
     * @return Redirects to the questions view.
     */
    @PostMapping("/selectCategory")
    public String selectCategory(@RequestParam String category, HttpSession httpSession) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        sessionHelper.selectCategory(category);
        return "redirect:/questions";
    }

    /**
     * Retrieves and displays questions for the selected category using the QuestionRetrievalService.
     *
     * @param model Model object to add attributes used for rendering views.
     * @param httpSession The session object containing the current quiz state.
     * @return The name of the view to be rendered or a redirect if conditions are not met.
     */
    @GetMapping("/questions")
    public String getQuestions(Model model, HttpSession httpSession) {
        String category = (String) httpSession.getAttribute("selectedCategory");

        if (category == null) {
            return "redirect:/start";
        }

        QuestionRetrievalService questionRetrievalService = new QuestionRetrievalService(httpSession, randomQuestionService);
        List<Question> randomQuestions = questionRetrievalService.retrieveQuestions(category);

        Integer currentQuestionIndex = (Integer) httpSession.getAttribute("currentQuestionIndex");
        if (currentQuestionIndex >= randomQuestions.size() - 1) {
            return "redirect:/congratulations";
        }

        addQuestionAndAnswersToModel(model, randomQuestions, currentQuestionIndex);
        return "questions";
    }


    /**
     * Sets the action state to 'answered' to proceed to the next question.
     *
     * @return Redirects to the questions page.
     */
    @GetMapping("/answerQuestion")
    public String answerQuestion() {
        httpSession.setAttribute("actionState", "answered");
        return "redirect:/questions";
    }

    /**
     * Displays the congratulations page upon successful completion of the quiz.
     * It adds user details to the model and resets the quiz-related session attributes.
     *
     * @param httpSession The session object containing the current quiz state and user details.
     * @param model The model object to add attributes used for rendering the congratulations view.
     * @return The name of the view to be rendered for the congratulations page.
     */
    @GetMapping("/congratulations")
    public String congratulations(HttpSession httpSession, Model model) {
        addUserDetailsToModel(httpSession, model);
        resetQuizSession(httpSession);
        return "congratulations";
    }

    /**
     * Displays the failed page upon unsuccessful completion of the quiz.
     * It adds user details to the model and resets the quiz-related session attributes.
     *
     * @param httpSession The session object containing the current quiz state and user details.
     * @param model The model object to add attributes used for rendering the failed view.
     * @return The name of the view to be rendered for the failed page.
     */
    @GetMapping("/failed")
    public String failed(HttpSession httpSession, Model model) {
        addUserDetailsToModel(httpSession, model);
        resetQuizSession(httpSession);
        return "failed";
    }

    /**
     * Displays the timesUp page when the time limit for the quiz is reached.
     * It adds user details to the model and resets the quiz-related session attributes.
     *
     * @param httpSession The session object containing the current quiz state and user details.
     * @param model The model object to add attributes used for rendering the timesUp view.
     * @return The name of the view to be rendered for the timesUp page.
     */
    @GetMapping("/timesUp")
    public String timesUp(HttpSession httpSession, Model model) {
        addUserDetailsToModel(httpSession, model);
        resetQuizSession(httpSession);
        return "timesUp";
    }

    /**
     * Adds user details to the model from the session using SessionHelper.
     *
     * @param httpSession The session object containing user details.
     * @param model The model to which user details will be added.
     */
    private void addUserDetailsToModel(HttpSession httpSession, Model model) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        String username = sessionHelper.getAttribute("username", String.class);
        Long userId = sessionHelper.getAttribute("userId", Long.class);
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);
    }

    /**
     * Resets the quiz-related attributes in the session using SessionHelper.
     *
     * @param httpSession The session object to be cleared.
     */
    private void resetQuizSession(HttpSession httpSession) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        sessionHelper.removeAttributes("randomQuestions", "currentQuestionIndex", "actionState", "selectedCategory");
    }

    /**
     * Adds the shuffled question and its answers to the model.
     *
     * @param model Model object to which question and answers will be added.
     * @param questions List of questions from which the current question is retrieved.
     * @param questionIndex Index of the current question in the list.
     */
    private void addQuestionAndAnswersToModel(Model model, List<Question> questions, int questionIndex) {
        Question currentQuestion = questions.get(questionIndex);
        List<Answer> shuffledAnswers = new ArrayList<>(currentQuestion.getAnswers());
        Collections.shuffle(shuffledAnswers);

        model.addAttribute("questionText", currentQuestion.getQuestionText());
        model.addAttribute("answers", shuffledAnswers);
    }

}