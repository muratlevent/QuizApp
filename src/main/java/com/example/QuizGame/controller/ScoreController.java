package com.example.QuizGame.controller;

import com.example.QuizGame.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing scores in the Quiz Game application.
 * This controller provides an API endpoint for saving and updating user scores.
 */
@RestController
@RequestMapping("/api")
public class ScoreController {

    private final ScoreService scoreService;

    /**
     * Constructor for ScoreController.
     *
     * @param scoreService The service for handling score related operations.
     */
    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * API endpoint for saving or updating a user's score.
     * This method calls the score service to update the high score for a given user.
     *
     * @param userId The ID of the user whose score is being updated.
     * @param score The score achieved by the user.
     * @param timeTaken The time taken by the user to achieve the score.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/saveScore")
    public ResponseEntity<?> saveScore(@RequestParam Long userId, @RequestParam int score, @RequestParam int timeTaken) {
        scoreService.updateHighScore(userId, score, timeTaken);
        return ResponseEntity.ok().build();
    }
}