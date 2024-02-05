package com.example.QuizGame.controller;

import com.example.QuizGame.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/saveScore")
    public ResponseEntity<?> saveScore(@RequestParam Long userId, @RequestParam int score, @RequestParam int timeTaken) {
        scoreService.updateHighScore(userId, score, timeTaken);
        return ResponseEntity.ok().build();
    }
}