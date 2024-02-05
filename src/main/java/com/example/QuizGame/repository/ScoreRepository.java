package com.example.QuizGame.repository;

import com.example.QuizGame.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findTopByUserIdOrderByScoreDesc(Long userId);
}
