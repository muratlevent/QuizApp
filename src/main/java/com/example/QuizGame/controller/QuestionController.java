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
import com.example.QuizGame.controller.SessionHelper;

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
     * Starts the quiz and initializes the session attributes.
     *
     * @param httpSession Session object to store quiz data.
     * @param model Model object to add attributes used for rendering views.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/start")
    public String startQuiz(HttpSession httpSession, Model model) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        sessionHelper.removeAttributes("randomQuestions", "currentQuestionIndex", "actionState");

        String username = sessionHelper.getAttribute("username", String.class);
        model.addAttribute("username", username);
        sessionHelper.setAttribute("gameScore", 0);
        sessionHelper.setAttribute("timeTaken", 0);

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
     * Handles the category selection for the quiz.
     *
     * @param category The selected quiz category.
     * @return Redirects to the questions page.
     */
    @PostMapping("/selectCategory")
    public String selectCategory(@RequestParam String category, HttpSession httpSession) {
        new CategorySelectionHelper(httpSession).selectCategory(category);
        return "redirect:/questions";
    }

    /**
     * Retrieves and displays questions for the selected category.
     *
     * @param model Model object to add attributes used for rendering views.
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

        Question currentQuestion = randomQuestions.get(currentQuestionIndex);
        List<Answer> shuffledAnswers = new ArrayList<>(currentQuestion.getAnswers());
        Collections.shuffle(shuffledAnswers);

        model.addAttribute("questionText", currentQuestion.getQuestionText());
        model.addAttribute("answers", shuffledAnswers);

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
     * Displays the congratulations page upon completing the quiz.
     *
     * @param model Model object to add attributes used for rendering views.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/congratulations")
    public String congratulations(HttpSession httpSession, Model model) {
        addUserDetailsToModel(httpSession, model);
        clearSession(httpSession);
        return "congratulations";
    }

    /**
     * Displays the failed page if the user fails the quiz.
     *
     * @param model Model object to add attributes used for rendering views.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/failed")
    public String failed(HttpSession httpSession, Model model) {
        addUserDetailsToModel(httpSession, model);
        clearSession(httpSession);
        return "failed";
    }

    /**
     * Displays the times up page if the user runs out of time.
     *
     * @param model Model object to add attributes used for rendering views.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/timesUp")
    public String timesUp(HttpSession httpSession, Model model) {
        addUserDetailsToModel(httpSession, model);
        clearSession(httpSession);
        return "timesUp";
    }

    private void addUserDetailsToModel(HttpSession httpSession, Model model) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        String username = sessionHelper.getAttribute("username", String.class);
        Long userId = sessionHelper.getAttribute("userId", Long.class);
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);
    }

    private void clearSession(HttpSession httpSession) {
        SessionHelper sessionHelper = new SessionHelper(httpSession);
        sessionHelper.removeAttributes("randomQuestions", "currentQuestionIndex", "actionState", "selectedCategory");
    }

}