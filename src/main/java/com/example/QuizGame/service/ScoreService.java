package com.example.QuizGame.service;

import com.example.QuizGame.model.Score;
import com.example.QuizGame.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    // Logic of updating the high score for a user
    // If the user doesn't have a high score, create a new one
    // If the user has a high score, update it if the new score is higher
    // If the user has a high score, don't update it if the new score is lower
    public void updateHighScore(Long userId, int score, int timeTaken) {
        Score highScore = scoreRepository.findTopByUserIdOrderByScoreDesc(userId).orElse(null);

        if (highScore == null) {
            highScore = new Score();
            highScore.setUserId(userId);
        } else if (score <= highScore.getScore()) {
            return;
        }

        highScore.setScore(score);
        highScore.setTimeTaken(timeTaken);
        highScore.setDate(new Timestamp(new Date().getTime()));

        scoreRepository.save(highScore);
    }
}
