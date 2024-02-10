package com.example.QuizGame.controller;

import com.example.QuizGame.dto.UserScoreDTO;
import com.example.QuizGame.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ScoreboardController {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreboardController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @GetMapping("/scoreboard")
    public String scoreboard(Model model) {
        List<UserScoreDTO> topScores = scoreRepository.findTop10ScoresWithUsernames(PageRequest.of(0, 10));
        model.addAttribute("scores", topScores);
        return "scoreboard";
    }
}
