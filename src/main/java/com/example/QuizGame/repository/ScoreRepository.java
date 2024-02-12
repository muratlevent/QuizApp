package com.example.QuizGame.repository;

import com.example.QuizGame.dto.UserScoreDTO;
import com.example.QuizGame.model.Score;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Score entities in the Quiz Game application.
 * This interface performing various operations on Score entities.
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    /**
     * Finds the top score for a given user, ordered by score in descending order.
     *
     * @param userId The ID of the user.
     * @return An Optional containing the top Score of the specified user if present.
     */
    Optional<Score> findTopByUserIdOrderByScoreDesc(Long userId);

    /**
     * Fetches all scores along with the usernames of the users who achieved them.
     * This query retrieves scores and associated user details.
     *
     * @return A list of UserScoreDTOs representing scores with user details.
     */
    @Query("SELECT new com.example.QuizGame.dto.UserScoreDTO(u.username, s.score, s.timeTaken, s.date) " +
            "FROM Score s JOIN s.user u " +
            "ORDER BY s.score DESC, s.date DESC")
    List<UserScoreDTO> findAllScoresWithUsernames();

    /**
     * Fetches the top 10 scores along with the usernames of the users who achieved them, based on the provided pageable.
     * This query retrieves scores and associated user details.
     *
     * @param pageable Pageable object to specify the number of records and sorting order.
     * @return A list of UserScoreDTOs representing the top scores with user details.
     */
    @Query("SELECT new com.example.QuizGame.dto.UserScoreDTO(u.username, s.score, s.timeTaken, s.date) " +
            "FROM Score s JOIN s.user u " +
            "ORDER BY s.score DESC, s.date DESC")
    List<UserScoreDTO> findTop10ScoresWithUsernames(Pageable pageable);

}
