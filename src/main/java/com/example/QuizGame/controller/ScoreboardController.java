package com.example.QuizGame.controller;

import com.example.QuizGame.dto.UserScoreDTO;
import com.example.QuizGame.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller for handling scoreboard display in the Quiz Game application.
 * This controller is responsible for fetching and displaying the top user scores.
 */
@Controller
public class ScoreboardController {

    private final ScoreRepository scoreRepository;

    /**
     * Constructor for ScoreboardController.
     *
     * @param scoreRepository Repository for accessing score data.
     */
    @Autowired
    public ScoreboardController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    /**
     * Handles requests to the scoreboard page.
     * Fetches the top 10 scores from the database and adds them to the model for display.
     *
     * @param model Model object to add attributes used for rendering views.
     * @return The name of the view for the scoreboard page.
     */
    @GetMapping("/scoreboard")
    public String scoreboard(Model model) {
        List<UserScoreDTO> topScores = scoreRepository.findTop10ScoresWithUsernames(PageRequest.of(0, 10));
        model.addAttribute("scores", topScores);
        return "scoreboard";
    }
}
