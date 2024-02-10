package com.example.QuizGame.repository;

import com.example.QuizGame.dto.UserScoreDTO;
import com.example.QuizGame.model.Score;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findTopByUserIdOrderByScoreDesc(Long userId);

    @Query("SELECT new com.example.QuizGame.dto.UserScoreDTO(u.username, s.score, s.timeTaken, s.date) " +
            "FROM Score s JOIN s.user u " +
            "ORDER BY s.score DESC, s.date DESC")
    List<UserScoreDTO> findAllScoresWithUsernames();

    @Query("SELECT new com.example.QuizGame.dto.UserScoreDTO(u.username, s.score, s.timeTaken, s.date) " +
            "FROM Score s JOIN s.user u " +
            "ORDER BY s.score DESC, s.date DESC")
    List<UserScoreDTO> findTop10ScoresWithUsernames(Pageable pageable);

}
