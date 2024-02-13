package com.example.QuizGame.service;

import com.example.QuizGame.model.Score;
import com.example.QuizGame.model.User;
import com.example.QuizGame.repository.ScoreRepository;
import com.example.QuizGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * Service for managing scores in the Quiz Game application.
 * This service is responsible for updating and maintaining high scores of users.
 */
@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for ScoreService.
     *
     * @param scoreRepository Repository for accessing and updating score data.
     * @param userRepository Repository for accessing user data.
     */
    @Autowired
    public ScoreService(ScoreRepository scoreRepository, UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    /**
     * Updates the high score for a specific user.
     * If the user's new score is higher than their previous high score,
     * it updates the high score with the new score and time taken.
     *
     * @param userId The ID of the user whose high score is to be updated.
     * @param score The new score achieved by the user.
     * @param timeTaken The time taken by the user to achieve the new score.
     */
    public void updateHighScore(Long userId, int score, int timeTaken) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            // Will handle the case where the user doesn't exist
            return;
        }
        User user = userOptional.get();

        Score highScore = scoreRepository.findTopByUserIdOrderByScoreDesc(userId)
                .orElse(null);

        if (highScore == null) {
            highScore = new Score();
            highScore.setUser(user);
        } else if (score <= highScore.getScore()) {
            return;
        }

        highScore.setScore(score);
        highScore.setTimeTaken(timeTaken);
        highScore.setDate(new Timestamp(new Date().getTime()));

        scoreRepository.save(highScore);
    }
}
