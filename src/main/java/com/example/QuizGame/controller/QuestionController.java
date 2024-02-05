package com.example.QuizGame.controller;

import com.example.QuizGame.model.Question;
import com.example.QuizGame.repository.*;
import com.example.QuizGame.service.RandomQuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/")
public class QuestionController {

    @Autowired
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final RandomQuestionService randomQuestionService;

    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository,
                              RandomQuestionService randomQuestionService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.randomQuestionService = randomQuestionService;
    }

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/start")
    public String startQuiz(HttpSession httpSession, Model model) {
        httpSession.removeAttribute("randomQuestions");
        httpSession.removeAttribute("currentQuestionIndex");
        httpSession.removeAttribute("actionState");
        String username = (String) httpSession.getAttribute("username");
        model.addAttribute("username", username);
        httpSession.setAttribute("gameScore", 0);
        httpSession.setAttribute("timeTaken", 0);

        return "start";
    }

    @GetMapping("/categories")
    public String showCategories() {
        return "categories";
    }

    @PostMapping("/selectCategory")
    public String selectCategory(@RequestParam String category) {
        httpSession.setAttribute("selectedCategory", category);
        return "redirect:/questions";
    }

    @GetMapping("/questions")
    public String getQuestions(Model model) {
        String category = (String) httpSession.getAttribute("selectedCategory");

        if (category == null) {
            return "redirect:/start";
        }

        List<Question> randomQuestions = (List<Question>) httpSession.getAttribute("randomQuestions");
        Integer currentQuestionIndex = (Integer) httpSession.getAttribute("currentQuestionIndex");
        String actionState = (String) httpSession.getAttribute("actionState");

        if (randomQuestions == null || currentQuestionIndex == null || actionState == null) {
            randomQuestions = randomQuestionService.getRandomQuestionsByCategory(3, category);
            httpSession.setAttribute("randomQuestions", randomQuestions);
            currentQuestionIndex = 0;
            httpSession.setAttribute("currentQuestionIndex", currentQuestionIndex);
            actionState = "viewing";
            httpSession.setAttribute("actionState", actionState);
        } else if ("answered".equals(actionState) && currentQuestionIndex < randomQuestions.size() - 1) {
            currentQuestionIndex++;
            httpSession.setAttribute("currentQuestionIndex", currentQuestionIndex);
            actionState = "viewing";
            httpSession.setAttribute("actionState", actionState);
        } else if (currentQuestionIndex >= randomQuestions.size() - 1) {
            return "redirect:/congratulations";
        }

        Question currentQuestion = randomQuestions.get(currentQuestionIndex);
        model.addAttribute("questionText", currentQuestion.getQuestionText());
        model.addAttribute("answers", currentQuestion.getAnswers());
        return "questions";
    }

    @GetMapping("/answerQuestion")
    public String answerQuestion() {
        httpSession.setAttribute("actionState", "answered");
        return "redirect:/questions";
    }

    @GetMapping("/congratulations")
    public String congratulations(Model model) {
        String username = (String) httpSession.getAttribute("username");
        model.addAttribute("username", username);
        Long userId = (Long) httpSession.getAttribute("userId");
        model.addAttribute("userId", userId);

        return "congratulations";
    }

    @GetMapping("/failed")
    public String failed(Model model) {
        String username = (String) httpSession.getAttribute("username");
        model.addAttribute("username", username);
        Long userId = (Long) httpSession.getAttribute("userId");
        model.addAttribute("userId", userId);
        httpSession.removeAttribute("randomQuestions");
        httpSession.removeAttribute("currentQuestionIndex");
        httpSession.removeAttribute("actionState");
        httpSession.removeAttribute("selectedCategory");
        return "failed";
    }

    @GetMapping("/timesUp")
    public String timesUp(Model model) {
        String username = (String) httpSession.getAttribute("username");
        model.addAttribute("username", username);
        Long userId = (Long) httpSession.getAttribute("userId");
        model.addAttribute("userId", userId);
        httpSession.removeAttribute("randomQuestions");
        httpSession.removeAttribute("currentQuestionIndex");
        httpSession.removeAttribute("actionState");
        httpSession.removeAttribute("selectedCategory");
        return "timesUp";
    }

    @GetMapping("/testRandomQuestionsByCategory")
    public ResponseEntity<List<Question>> testRandomQuestionsByCategory(@RequestParam String category,
                                                                        @RequestParam int count) {
        List<Question> randomQuestions = randomQuestionService.getRandomQuestionsByCategory(count, category);
        return ResponseEntity.ok(randomQuestions);
    }

}